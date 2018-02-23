package neil.com.kotlinone.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import neil.com.kotlinone.MainActivity
import neil.com.kotlinone.R
import neil.com.kotlinone.adapter.rv.BannerAdapter
import neil.com.kotlinone.adapter.rv.HomeAdapter
import neil.com.kotlinone.base.BaseFragment
import neil.com.kotlinone.base.Preference
import neil.com.kotlinone.bean.client.BannerResponse
import neil.com.kotlinone.bean.client.Datas
import neil.com.kotlinone.bean.client.HomeListResponse
import neil.com.kotlinone.constant.Constant
import neil.com.kotlinone.presenter.impl.HomePresenterImpl
import neil.com.kotlinone.ui.activity.ContentActivity
import neil.com.kotlinone.view.client.CollectArticleView
import neil.com.kotlinone.view.client.HomeFragmentView
import neil.com.kotlinone.widget.HorizontalRecyclerView

/**
 * 主页fragment
 * @author neil
 * @date 2018/2/22
 */
class HomeFragment : BaseFragment(), HomeFragmentView, CollectArticleView {

    companion object {
        private const val BANNER_TIME = 5000L
    }

    private var mainView: View? = null

    // 数据集
    private val datas = mutableListOf<Datas>()

    private val homeFragmentPresenter: HomePresenterImpl by lazy {
        HomePresenterImpl(this, this)
    }

    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(activity, datas)
    }

    private val isLogin: Boolean by Preference(Constant.LOGIN_KEY, false)

    // banner
    private lateinit var bannerRecyclerView: HorizontalRecyclerView
    private val bannerDatas = mutableListOf<BannerResponse.Data>()
    private val bannerAdapter: BannerAdapter by lazy {
        BannerAdapter(activity, bannerDatas)
    }
    private val bannerPagerSnap: PagerSnapHelper by lazy {
        PagerSnapHelper()
    }
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    }
    // banner switch job
    private var bannerSwotchJob: Job? = null
    private var currentIndex = 0


    override fun onCreateView(
            inflater: LayoutInflater?,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        mainView ?: let {
            mainView = inflater?.inflate(R.layout.fragment_home, container, false)
            bannerRecyclerView = LayoutInflater.from(activity).inflate(R.layout.home_banner, null) as HorizontalRecyclerView
        }
        return mainView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        swipeRefreshLayout.run {
            isRefreshing = true
            setOnRefreshListener(onRefreshListener)
        }

        recyclerView.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = homeAdapter
        }

        bannerRecyclerView.run {
            layoutManager = linearLayoutManager
            bannerPagerSnap.attachToRecyclerView(this)
            requestDisallowInterceptTouchEvent(true)
            setOnTouchListener(onTouchListener)  // 添加触摸事件
            addOnScrollListener(onScrollListener) // 添加滑动事件监听
        }

        bannerAdapter.run {
            bindToRecyclerView(bannerRecyclerView)
            onItemClickListener = this@HomeFragment.onBannerItemClickListener
        }

        homeAdapter.run {
            bindToRecyclerView(recyclerView)
            setOnLoadMoreListener(onRequestLoadMoreListener, recyclerView)
            onItemClickListener = this@HomeFragment.onItemClickListener
            onItemChildClickListener = this@HomeFragment.onItemChildClickListener
            addHeaderView(bannerRecyclerView)
//            setEmptyView(R.layout.fragment_home_empty)
        }
        homeFragmentPresenter.getBanner()
        homeFragmentPresenter.getHomeList()

    }

    /**
     * LoadMoreListener
     */
    private val onRequestLoadMoreListener = BaseQuickAdapter.RequestLoadMoreListener {
        val page = homeAdapter.data.size / 20 + 1
        homeFragmentPresenter.getHomeList(page)
    }

    // banner 点击事件
    private val onBannerItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
        if (bannerDatas.size != 0) {
            Intent(activity, MainActivity::class.java).run {
                putExtra(Constant.CONTENT_URL_KEY, bannerDatas[position].url)
                putExtra(Constant.CONTENT_TITLE_KEY, bannerDatas[position].title)
                startActivity(this)
            }
        }
    }

    // 内容点击事件
    private val onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
        if (datas.size != 0) {
            Intent(activity, ContentActivity::class.java).run {
                putExtra(Constant.CONTENT_URL_KEY, datas[position].link)
                putExtra(Constant.CONTENT_ID_KEY, datas[position].id)
                putExtra(Constant.CONTENT_TITLE_KEY, datas[position].title)
                startActivity(this)
            }
        }
    }

    // 子view点击事件
    private val onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->

    }


    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        refreshData()
    }

    // 触摸事件监听
    private val onTouchListener = View.OnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                cancelSwitchJob()
            }
        }
        false
    }

    // 滑动事件监听
    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            when (newState) {
                RecyclerView.SCROLL_STATE_IDLE -> {
                    currentIndex = linearLayoutManager.findFirstVisibleItemPosition()
                    startSwitchJob()
                }
            }
        }
    }

    // resume banner switch
    private fun startSwitchJob() = bannerSwotchJob?.run {
        if (!isActive) {
            bannerSwotchJob = getBannerSwitchJob().apply {
                start()
            }
        }
    } ?: let {
        bannerSwotchJob = getBannerSwitchJob().apply {
            start()
        }
    }

    // 获取banner 协程任务
    private fun getBannerSwitchJob() = launch {
        repeat(Int.MAX_VALUE) {
            if (bannerDatas.size == 0) {
                return@launch
            }
            delay(BANNER_TIME)
            currentIndex++
            val index = currentIndex % bannerDatas.size
            bannerRecyclerView.smoothScrollToPosition(index)
            currentIndex = index
        }
    }

    // 取消banner 协程任务
    private fun cancelSwitchJob() = bannerSwotchJob?.run {
        if (isActive) {
            cancel()
        }
    }

    // refresh
    fun refreshData() {
        swipeRefreshLayout.isRefreshing = true
        homeAdapter.setEnableLoadMore(false)
        cancelSwitchJob()
        homeFragmentPresenter.getBanner()
        homeFragmentPresenter.getHomeList()
    }

    fun smoothScrollToPosition() = recyclerView.smoothScrollToPosition(0)

    override fun onPause() {
        super.onPause()
        cancelSwitchJob()
    }

    override fun onResume() {
        super.onResume()
        startSwitchJob()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            cancelSwitchJob()
        } else {
            startSwitchJob()
        }
    }

    override fun getHomeListSuccess(result: HomeListResponse) {
        result.data.datas?.let {
            homeAdapter.run {
                // 列表总数
                val total = result.data.total
                // 当前总数
                if (result.data.offset >= total || data.size >= total) {
                    loadMoreEnd()
                    return@let
                }
                // 如果swipRefresh 正在刷新
                if (swipeRefreshLayout.isRefreshing) {
                    replaceData(it)
                } else {
                    addData(it)
                }
                loadMoreComplete()
                setEnableLoadMore(true)
            }
        }
        swipeRefreshLayout.isRefreshing = false
        // it?.datas?.let { it1 -> homeAdapter.replaceData(it1) }
    }

    override fun getHomeListFailed(errorMessage: String?) {
    }

    override fun getHomeListZero() {
    }

    override fun getHomeSmall(result: HomeListResponse) {
    }

    override fun getBannerSuccess(result: BannerResponse) {
        result.data?.let {
            bannerAdapter.replaceData(it)
            startSwitchJob()
        }
    }

    override fun getBannerFailed(errorMessage: String?) {
    }

    override fun getBannerZero() {
    }

    override fun cancelRequest() {
        homeFragmentPresenter.cancelRequest()
    }

    override fun collectArticleSuccess(result: HomeListResponse, isAdd: Boolean) {
    }

    override fun collectArticleFailed(errorMessage: String?, isAdd: Boolean) {
    }

}