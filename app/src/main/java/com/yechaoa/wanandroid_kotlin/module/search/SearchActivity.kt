package com.yechaoa.wanandroid_kotlin.module.search

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.yechaoa.wanandroid_kotlin.R
import com.yechaoa.wanandroid_kotlin.adapter.ArticleAdapter
import com.yechaoa.wanandroid_kotlin.base.BaseActivity
import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.bean.Article
import com.yechaoa.wanandroid_kotlin.bean.ArticleDetail
import com.yechaoa.wanandroid_kotlin.bean.Hotkey
import com.yechaoa.wanandroid_kotlin.module.detail.DetailActivity
import com.yechaoa.wanandroid_kotlin.module.login.LoginActivity
import com.yechaoa.yutilskt.LogUtil
import com.yechaoa.yutilskt.ToastUtil
import com.yechaoa.yutilskt.YUtils
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import kotlinx.android.synthetic.main.activity_search.*
import java.util.*

class SearchActivity : BaseActivity(), ISearchView, OnItemClickListener, OnLoadMoreListener,
    OnItemChildClickListener, TagFlowLayout.OnTagClickListener {

    lateinit var mSearchPresenter: SearchPresenter
    private lateinit var mDataList: MutableList<ArticleDetail>
    private lateinit var mArticleAdapter: ArticleAdapter
    private lateinit var mKey: String
    private var mPosition: Int = 0
    private lateinit var mHotkeyList: MutableList<Hotkey>

    companion object {
        private const val TOTAL_COUNTER = 20//每次加载数量
        private var CURRENT_SIZE = 0//当前加载数量
        private var CURRENT_PAGE = 0//当前加载页数
    }

    override fun createPresenter() {
        mSearchPresenter = SearchPresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun initView() {
        setMyTitle("搜索")
        setBackEnabled()

        mSearchPresenter.getHotkey()
    }

    private lateinit var mEditText: EditText

    /**
     * 添加SearchView
     * SearchView使用参考：https://blog.csdn.net/yechaoa/article/details/80658940
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //引用menu文件
        menuInflater.inflate(R.menu.menu_search, menu)

        //找到SearchView并配置相关参数
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView: SearchView = MenuItemCompat.getActionView(searchItem) as SearchView

        searchView.apply {
            //搜索图标是否显示在搜索框内
            setIconifiedByDefault(true)
            //设置搜索框展开时是否显示提交按钮，可不显示
            isSubmitButtonEnabled = true
            //让键盘的回车键设置成搜索
            imeOptions = EditorInfo.IME_ACTION_SEARCH
            //搜索框是否展开，false表示展开
            isIconified = false
            //获取焦点
            isFocusable = true
            requestFocusFromTouch()
            //设置提示词
            queryHint = "请输入关键字"
        }

        //设置输入框文字颜色
        mEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text) as EditText
        mEditText.setHintTextColor(ContextCompat.getColor(this, R.color.white30))
        mEditText.setTextColor(ContextCompat.getColor(this, R.color.white))

        //设置搜索文本监听
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            // 当搜索内容改变时触发该方法
            override fun onQueryTextChange(newText: String): Boolean {
                //当没有输入任何内容的时候显示搜索热词，看实际需求
                ll_hotkey.visibility = View.VISIBLE
                recycler_view.visibility = View.GONE
                return false
            }

            // 当点击搜索按钮时触发该方法
            override fun onQueryTextSubmit(query: String): Boolean {
                LogUtil.i("aaa", "搜索内容===$query")
                mKey = query
                CURRENT_PAGE = 0 //重置分页，避免二次加载分页混乱
                //搜索请求
                mSearchPresenter.getArticleList(CURRENT_PAGE, mKey)
                //清除焦点，收软键盘
                searchView.clearFocus()
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    /**
     * 初始化搜索热词
     */
    override fun getHotkey(hotkey: BaseBean<MutableList<Hotkey>>) {
        mHotkeyList = hotkey.data
        flow_layout.adapter = object : TagAdapter<Hotkey>(mHotkeyList) {
            override fun getView(parent: FlowLayout, position: Int, s: Hotkey): View {
                val tvTag = LayoutInflater.from(this@SearchActivity).inflate(
                    R.layout.item_navi,
                    flow_layout, false
                ) as TextView
                tvTag.text = s.name
                tvTag.setTextColor(randomColor())
                return tvTag
            }
        }
        //设置点击事件
        flow_layout.setOnTagClickListener(this)
    }

    /**
     * 随机颜色
     */
    fun randomColor(): Int {
        Random().run {
            //rgb取值0-255，但是值过大,就越接近白色,会看不清,所以限制在200
            val red = nextInt(200)
            val green = nextInt(200)
            val blue = nextInt(200)
            return Color.rgb(red, green, blue)
        }
    }

    override fun getHotkeyError(msg: String) {
        ToastUtil.showCenter(msg)
    }

    override fun getArticleList(article: BaseBean<Article>) {
        recycler_view.visibility = View.VISIBLE
        ll_hotkey.visibility = View.GONE

        CURRENT_SIZE = article.data.datas.size
        mDataList = article.data.datas
        mArticleAdapter = ArticleAdapter().apply {
            animationEnable = true
            //item点击事件
            setOnItemClickListener(this@SearchActivity)
            //item子view点击事件
            setOnItemChildClickListener(this@SearchActivity)
            //加载更多
            loadMoreModule.setOnLoadMoreListener(this@SearchActivity)
        }
        recycler_view.adapter = mArticleAdapter
        mArticleAdapter.setList(mDataList)
    }

    override fun getArticleError(msg: String) {
        ToastUtil.showCenter(msg)
    }

    override fun getArticleMoreList(article: BaseBean<Article>) {
        CURRENT_SIZE = article.data.datas.size
        mDataList.addAll(article.data.datas)
        mArticleAdapter.addData(article.data.datas)
        mArticleAdapter.loadMoreModule.loadMoreComplete()
    }

    override fun getArticleMoreError(msg: String) {
        ToastUtil.showCenter(msg)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(DetailActivity.WEB_URL, mDataList[position].link)
            putExtra(DetailActivity.WEB_TITLE, mDataList[position].title)
        }
        startActivity(intent)
    }

    override fun onLoadMore() {
        if (CURRENT_SIZE < TOTAL_COUNTER) {
            mArticleAdapter.loadMoreModule.loadMoreEnd(true)
        } else {
            CURRENT_PAGE++
            mSearchPresenter.getArticleMoreList(CURRENT_PAGE, mKey)
        }
    }

    /**
     * 未登录收藏
     */
    override fun login(msg: String) {
        showLoginDialog(msg)
    }

    private fun showLoginDialog(msg: String) {
        val builder = AlertDialog.Builder(this@SearchActivity).apply {
            setTitle("提示")
            setMessage(msg)
            setPositiveButton("确定") { _, _ ->
                startActivity(Intent(this@SearchActivity, LoginActivity::class.java))
            }
            setNegativeButton("取消", null)
        }
        builder.create().show()
    }

    override fun collect(msg: String) {
        ToastUtil.showCenter(msg)
        mDataList[mPosition].collect = true
        mArticleAdapter.notifyDataSetChanged()
    }

    override fun unCollect(msg: String) {
        ToastUtil.showCenter(msg)
        mDataList[mPosition].collect = false
        mArticleAdapter.notifyDataSetChanged()
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        mPosition = position
        if (mDataList[position].collect) {
            mSearchPresenter.unCollect(mDataList[position].id)
        } else {
            mSearchPresenter.collect(mDataList[position].id)
        }
    }

    /**
     * 热词点击事件
     */
    override fun onTagClick(view: View?, position: Int, parent: FlowLayout?): Boolean {
        YUtils.closeSoftKeyboard()
        mKey = mHotkeyList[position].name
        //填充搜索框
        mEditText.setText(mKey)
        CURRENT_PAGE = 0 //重置分页，避免二次加载分页混乱
        mSearchPresenter.getArticleList(CURRENT_PAGE, mKey)
        return true
    }
}
