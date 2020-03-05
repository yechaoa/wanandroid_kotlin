package com.yechaoa.wanandroid_kotlin.module.search

import android.content.Intent
import android.view.Menu
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.yechaoa.wanandroid_kotlin.R
import com.yechaoa.wanandroid_kotlin.adapter.ArticleAdapter
import com.yechaoa.wanandroid_kotlin.base.BaseActivity
import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.bean.Article
import com.yechaoa.wanandroid_kotlin.module.detail.DetailActivity
import com.yechaoa.yutilskt.LogUtilKt
import com.yechaoa.yutilskt.ToastUtilKt
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity(), ISearchView {

    lateinit var mSearchPresenter: SearchPresenter

    override fun createPresenter() {
        mSearchPresenter = SearchPresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun initView() {
        setMyTitle("搜索")
        setBackEnabled()

        recycler_view.layoutManager=LinearLayoutManager(this)
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
//                mSearchPresenter.getArticleList(0, "")
                return false
            }

            // 当点击搜索按钮时触发该方法
            override fun onQueryTextSubmit(query: String): Boolean {
                LogUtilKt.i("aaa","搜索内容===$query")

                //搜索
                mSearchPresenter.getArticleList(0, query)
                //清除焦点，收软键盘
                searchView.clearFocus();
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }


    override fun getArticleList(article: BaseBean<Article>) {
        val datas = article.data.datas
        val articleAdapter = ArticleAdapter(datas)
        articleAdapter.animationEnable = true

        //item点击事件
        articleAdapter.setOnItemClickListener { adapter, view, position ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.WEB_URL, datas[position].link)
            intent.putExtra(DetailActivity.WEB_TITLE, datas[position].title)
            startActivity(intent)
        }

        recycler_view.adapter = articleAdapter

    }

    override fun getArticleError(msg: String) {
        ToastUtilKt.showCenterToast(msg)
    }
}
