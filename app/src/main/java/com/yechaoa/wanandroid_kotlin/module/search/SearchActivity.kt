package com.yechaoa.wanandroid_kotlin.module.search

import android.content.Intent
import android.view.Menu
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.yechaoa.wanandroid_kotlin.R
import com.yechaoa.wanandroid_kotlin.adapter.ArticleAdapter
import com.yechaoa.wanandroid_kotlin.base.BaseActivity
import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.bean.Article
import com.yechaoa.wanandroid_kotlin.bean.ArticleDetail
import com.yechaoa.wanandroid_kotlin.module.detail.DetailActivity
import com.yechaoa.yutilskt.LogUtilKt
import com.yechaoa.yutilskt.ToastUtilKt
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity(), ISearchView, OnItemClickListener, OnLoadMoreListener {

    lateinit var mSearchPresenter: SearchPresenter
    lateinit var mDataList: MutableList<ArticleDetail>
    private lateinit var mArticleAdapter: ArticleAdapter
    private lateinit var mKey: String

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

        recycler_view.layoutManager = LinearLayoutManager(this)
    }

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

        //搜索图标是否显示在搜索框内
        searchView.setIconifiedByDefault(true)
        //设置搜索框展开时是否显示提交按钮，可不显示
        searchView.isSubmitButtonEnabled = true;
        //让键盘的回车键设置成搜索
        searchView.imeOptions = EditorInfo.IME_ACTION_SEARCH;
        //搜索框是否展开，false表示展开
        searchView.isIconified = false;
        //获取焦点
        searchView.isFocusable = true;
        searchView.requestFocusFromTouch();
        //设置提示词
        searchView.queryHint = "请输入关键字";

        //设置输入框文字颜色
        val editText = searchView.findViewById(androidx.appcompat.R.id.search_src_text) as EditText
        editText.setHintTextColor(ContextCompat.getColor(this, R.color.white));
        editText.setTextColor(ContextCompat.getColor(this, R.color.white));

        //设置搜索文本监听
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            // 当搜索内容改变时触发该方法
            override fun onQueryTextChange(newText: String): Boolean {
                //当没有输入任何内容的时候清除结果，看实际需求
                //mSearchPresenter.getArticleList(0, "")
                return false
            }

            // 当点击搜索按钮时触发该方法
            override fun onQueryTextSubmit(query: String): Boolean {
                LogUtilKt.i("aaa", "搜索内容===$query")
                mKey = query
                //搜索请求
                mSearchPresenter.getArticleList(0, mKey)
                //清除焦点，收软键盘
                searchView.clearFocus();
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun getArticleList(article: BaseBean<Article>) {
        CURRENT_SIZE = article.data.datas.size
        mDataList = article.data.datas
        mArticleAdapter = ArticleAdapter(mDataList)
        mArticleAdapter.animationEnable = true

        //item点击事件
        mArticleAdapter.setOnItemClickListener(this)

        //加载更多
        mArticleAdapter.loadMoreModule?.setOnLoadMoreListener(this)

        recycler_view.adapter = mArticleAdapter
    }

    override fun getArticleError(msg: String) {
        ToastUtilKt.showCenterToast(msg)
    }

    override fun getArticleMoreList(article: BaseBean<Article>) {
        CURRENT_SIZE = article.data.datas.size
        mDataList.addAll(article.data.datas)
        mArticleAdapter.addData(article.data.datas)
        mArticleAdapter.loadMoreModule?.loadMoreComplete()
    }

    override fun getArticleMoreError(msg: String) {
        ToastUtilKt.showCenterToast(msg)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.WEB_URL, mDataList[position].link)
        intent.putExtra(DetailActivity.WEB_TITLE, mDataList[position].title)
        startActivity(intent)
    }

    override fun onLoadMore() {
        if (CURRENT_SIZE < TOTAL_COUNTER) {
            mArticleAdapter.loadMoreModule?.loadMoreEnd(true)
        } else {
            CURRENT_PAGE++
            mSearchPresenter.getArticleMoreList(CURRENT_PAGE, mKey)
        }
    }
}
