package neil.com.kotlinone.presenter.impl

import neil.com.kotlinone.bean.client.BannerResponse
import neil.com.kotlinone.bean.client.HomeListResponse
import neil.com.kotlinone.model.HomeModel
import neil.com.kotlinone.model.impl.HomeModelImpl
import neil.com.kotlinone.presenter.client.HomePresenter
import neil.com.kotlinone.view.client.CollectArticleView
import neil.com.kotlinone.view.client.HomeFragmentView

/**
 *
 * @author neil
 * @date 2018/2/22
 */
class HomePresenterImpl(
        val homeFragmentView: HomeFragmentView,
        val collectArticleView: CollectArticleView) :
        HomePresenter.OnHomeListListener, HomePresenter.OnBannerListener {

    private val homeModel: HomeModel = HomeModelImpl()

    override fun getHomeList(page: Int) {
        homeModel.getHomeList(this, page)
    }

    override fun getHomeListSuccess(result: HomeListResponse) {
        if (result.errorCode != 0) {
            homeFragmentView.getHomeListFailed(result.errorMsg)
            return
        }

        // 列表总数
        val total = result.data.total
        if (total == 0) {
            homeFragmentView.getHomeListZero()
        } else if (total < result.data.size) {
            // 已经加载完成
            homeFragmentView.getHomeSmall(result)
        } else {
            homeFragmentView.getHomeListSuccess(result)
        }

        // when lambdas表达式
//        when {
//            total == 0 -> homeFragmentView.getHomeListZero()
//            total < result.data.size -> // 已经加载完成
//                homeFragmentView.getHomeSmall(result)
//            else -> homeFragmentView.getHomeListSuccess(result)
//        }
    }

    override fun getHomeListFailed(errorMessage: String) {
        homeFragmentView.getHomeListFailed(errorMessage)
    }

    override fun getBanner() {
        homeModel.getBanner(this)
    }

    override fun getBannerSuccess(result: BannerResponse) {
        if (result.errorCode != 0) {
            homeFragmentView.getBannerFailed(result.errorMsg)
            return
        }
        if (result.data?.size == 0 || result.data == null) {
            homeFragmentView.getBannerZero()
            return
        }
        homeFragmentView.getBannerSuccess(result)
    }

    override fun getBannerFailed(errorMessage: String?) {
        homeFragmentView.getBannerFailed(errorMessage)
    }

    fun cancelRequest(){
        homeModel.cancelBannerRequest()
        homeModel.cancelHomeListRequest()
    }

}