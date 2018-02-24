package neil.com.kotlinone.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_type.*
import neil.com.kotlinone.R
import neil.com.kotlinone.adapter.rv.TypeAdapter
import neil.com.kotlinone.base.BaseFragment
import neil.com.kotlinone.bean.client.TreeListResponse
import neil.com.kotlinone.constant.Constant
import neil.com.kotlinone.presenter.impl.TypeFragmentPresenterImpl
import neil.com.kotlinone.toast
import neil.com.kotlinone.ui.activity.TypeContentActivity
import neil.com.kotlinone.view.client.TypeFragmentView

/**
 * 文章类型type
 * @author neil
 * @date 2018/2/23
 */
class TypeFragment : BaseFragment(), TypeFragmentView {

    private var mainView: View? = null
    private val datas = mutableListOf<TreeListResponse.Data>()
    private val typeFragmentPresenter: TypeFragmentPresenterImpl by lazy {
        TypeFragmentPresenterImpl(this)
    }
    private val typeAdapter: TypeAdapter by lazy {
        TypeAdapter(activity, datas)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainView ?: let {
            mainView = inflater?.inflate(R.layout.fragment_type, container, false)
        }
        return mainView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        typeSwipeRefreshLayout.run {
            isRefreshing = true
            setOnRefreshListener(onRefreshListener)
        }

        typeRecyclerView.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = typeAdapter
        }

        typeAdapter.run {
            bindToRecyclerView(typeRecyclerView)
            setEmptyView(R.layout.fragment_home_empty)
            onItemClickListener = this@TypeFragment.onItemClickListener
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden && isFirst) {
            typeFragmentPresenter.getTypeTreeList()
            isFirst = false
        }
    }

    override fun getTypeListSuccess(result: TreeListResponse) {
        result.data.let {
            if (typeSwipeRefreshLayout.isRefreshing) {
                typeAdapter.replaceData(it)
            } else {
                typeAdapter.addData(it)
            }
        }
        typeSwipeRefreshLayout.isRefreshing = false
    }

    override fun getTypeListFail(errorMessage: String?) {
        errorMessage?.let {
            activity.toast(it)
        } ?: let {
            activity.toast(getString(R.string.get_data_error))
        }
        typeSwipeRefreshLayout.isRefreshing = false
    }

    override fun getTypeListZero() {
        activity.toast(getString(R.string.get_data_zero))
        typeSwipeRefreshLayout.isRefreshing = false
    }

    override fun cancelRequest() {
        typeFragmentPresenter.cancelRequest()
    }


    fun smoothScrollToPosition() = typeRecyclerView.smoothScrollToPosition(0)

    /**
     * RefreshListener
     */
    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        refreshData()
    }

    fun refreshData() {
        typeSwipeRefreshLayout.isRefreshing = true
        typeFragmentPresenter.getTypeTreeList()
    }

    private val onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
        if (datas.size != 0) {
            Intent(activity, TypeContentActivity::class.java).run {
                putExtra(Constant.CONTENT_TITLE_KEY, datas[position].name)
                putExtra(Constant.CONTENT_CHILDREN_DATA_KEY, datas[position])
                startActivity(this)
            }
        }
    }
}