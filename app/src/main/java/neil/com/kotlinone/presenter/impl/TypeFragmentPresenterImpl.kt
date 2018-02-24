package neil.com.kotlinone.presenter.impl

import neil.com.kotlinone.bean.client.TreeListResponse
import neil.com.kotlinone.model.HomeModel
import neil.com.kotlinone.model.impl.HomeModelImpl
import neil.com.kotlinone.presenter.client.HomePresenter
import neil.com.kotlinone.view.client.TypeFragmentView

/**
 * 文章类型 presenter
 * @author neil
 * @date 2018/2/24
 */
class TypeFragmentPresenterImpl(val typeFragmentView: TypeFragmentView) : HomePresenter.OnTypeTreeListListener {

    private val homeModel: HomeModel = HomeModelImpl()

    override fun getTypeTreeList() {
        homeModel.getTypeTreeList(this)
    }

    // 回调数据结果成功
    override fun getTypeTreeListSuccess(result: TreeListResponse) {
        if (result.errorCode != 0) {
            typeFragmentView.getTypeListFail(result.errorMsg)
            return
        }
        if (result.data.isEmpty()) {
            typeFragmentView.getTypeListZero()
            return
        }
        typeFragmentView.getTypeListSuccess(result)
    }

    override fun getTypeTreeListFailed(errorMessage: String?) {
        typeFragmentView.getTypeListFail(errorMessage)
    }

    fun cancelRequest() {
        homeModel.cancelTypeTreeRequest()
    }

}