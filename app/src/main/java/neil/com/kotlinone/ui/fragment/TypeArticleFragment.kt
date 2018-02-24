package neil.com.kotlinone.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_type_content.*
import neil.com.kotlinone.R
import neil.com.kotlinone.adapter.TypeArticlePagerAdapter
import neil.com.kotlinone.adapter.rv.TypeArticleAdapter
import neil.com.kotlinone.base.BaseFragment
import neil.com.kotlinone.base.Preference
import neil.com.kotlinone.bean.client.ArticleListResponse
import neil.com.kotlinone.bean.client.Datas
import neil.com.kotlinone.bean.client.HomeListResponse
import neil.com.kotlinone.constant.Constant
import neil.com.kotlinone.presenter.impl.TypeArticlePresenterImpl
import neil.com.kotlinone.toast
import neil.com.kotlinone.ui.activity.ContentActivity
import neil.com.kotlinone.view.client.CollectArticleView
import neil.com.kotlinone.view.client.TypeArticleFragmentView

/**
 * 文章类型列表fragment
 * @author neil
 * @date 2018/2/24
 */
class TypeArticleFragment :
        BaseFragment(), TypeArticleFragmentView, CollectArticleView {

    private var mainView: View? = null

    private val datas = mutableListOf<Datas>()

    private val typeArticlePresenter: TypeArticlePresenterImpl by lazy {
        TypeArticlePresenterImpl(this, this)
    }

    private val typeArticleAdapter: TypeArticleAdapter by lazy {
        TypeArticleAdapter(activity, datas)
    }

    private var cid: Int = 0
    private val isLogin: Boolean by Preference(Constant.LOGIN_KEY, false)

    override fun onCreateView(
            inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainView ?: let {
            mainView = inflater?.inflate(R.layout.fragment_type_content, container, false)
        }
        return mainView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        cid = arguments.getInt(Constant.CONTENT_CID_KEY)
        tabSwipeRefreshLayout.run {
            isRefreshing = true
            setOnRefreshListener(onRefreshListener)
        }
        tabRecyclerView.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = typeArticleAdapter
        }

        typeArticleAdapter.run {
            setOnLoadMoreListener(onRequestLoadMoreListener, tabRecyclerView)
            onItemClickListener = this@TypeArticleFragment.onItemClickListener
            onItemChildClickListener = this@TypeArticleFragment.onItemChildClickListener
            setEmptyView(R.layout.fragment_home_empty)
        }
        typeArticlePresenter.getTypeArticleList(cid = cid)
    }

    override fun getTypeArticleListSuccess(result: ArticleListResponse) {
        result.data.datas?.let {
            typeArticleAdapter.run {
                val total = result.data.total
                if (result.data.offset >= total || data.size >= total) {
                    loadMoreEnd()
                    return@let
                }
                if (tabSwipeRefreshLayout.isRefreshing) {
                    replaceData(it)
                } else {
                    addData(it)
                }
                loadMoreComplete()
                setEnableLoadMore(true)
            }
        }
        tabSwipeRefreshLayout.isRefreshing = false
    }

    override fun getTypeArticleListFailed(errorMessage: String?) {
        typeArticleAdapter.setEnableLoadMore(false)
        typeArticleAdapter.loadMoreFail()
        errorMessage?.let {
            activity.toast(it)
        } ?: let {
            activity.toast(getString(R.string.get_data_error))
        }
        tabSwipeRefreshLayout.isRefreshing = false
    }

    override fun getTypeArticleListZero() {
        activity.toast(getString(R.string.get_data_zero))
        tabSwipeRefreshLayout.isRefreshing = false
    }

    // 加载数量少于分页数量，即加载完成
    override fun getTypeArticleListSmall(result: ArticleListResponse) {
        result.data.datas?.let {
            typeArticleAdapter.run {
                replaceData(it)
                loadMoreComplete()
                loadMoreEnd()
                setEnableLoadMore(false)
            }
        }
        tabSwipeRefreshLayout.isRefreshing = false
    }

    override fun collectArticleSuccess(result: HomeListResponse, isAdd: Boolean) {
        activity.toast(
                if (isAdd) {
                    activity.getString(R.string.bookmark_cancel_success)
                } else {
                    activity.getString(R.string.bookmark_cancel_success)
                }
        )
    }

    override fun collectArticleFailed(errorMessage: String?, isAdd: Boolean) {
        activity.toast(
                if (isAdd) {
                    activity.getString(R.string.bookmark_failed, errorMessage)
                } else {
                    activity.getString(R.string.bookmark_cancel_failed, errorMessage)
                }
        )
    }

    override fun cancelRequest() {
        typeArticlePresenter.cancelRequest()
        typeArticleAdapter.loadMoreComplete()
        tabSwipeRefreshLayout.isRefreshing = false
    }

    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        tabSwipeRefreshLayout.isRefreshing = true
        typeArticleAdapter.setEnableLoadMore(false)
        typeArticlePresenter.getTypeArticleList(cid = cid)
    }

    /**
     * 加载更多 LoadMoreListener
     */
    private val onRequestLoadMoreListener = BaseQuickAdapter.RequestLoadMoreListener {
        val page = typeArticleAdapter.data.size / 20 + 1
        typeArticlePresenter.getTypeArticleList(page, cid)
    }

    /**
     * ItemClickListener
     */
    private val onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
        if (datas.size != 0) {
            Intent(activity, ContentActivity::class.java).run {
                putExtra(Constant.CONTENT_URL_KEY, datas[position].link)
                putExtra(Constant.CONTENT_ID_KEY, datas[position].id)
                putExtra(Constant.CONTENT_TITLE_KEY, datas[position].title)
                startActivity(this)
            }
        }
    }

    /**
     * ItemChildClickListener
     */
    private val onItemChildClickListener =
            BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
                //                if (datas.size != 0) {
//                    val data = datas[position]
//                    when (view.id) {
//                        R.id.homeItemLike -> {
//                            if (isLogin) {
//                                val collect = data.collect
//                                data.collect = !collect
//                                typeArticleAdapter.setData(position, data)
//                                typeArticlePresenter.collectArticle(data.id, !collect)
//                            } else {
//                                Intent(activity, LoginActivity::class.java).run {
//                                    startActivity(this)
//                                }
//                                activity.toast(getString(R.string.login_please_login))
//                            }
//                        }
//                    }
//                }
            }


    companion object {
        fun newInstance(cid: Int): TypeArticleFragment {
            val fragment = TypeArticleFragment()
            var args = Bundle()
            args.putInt(Constant.CONTENT_CID_KEY, cid)
            fragment.arguments = args
            return fragment
        }
    }
}
