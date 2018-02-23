package neil.com.kotlinone.model

import neil.com.kotlinone.presenter.client.HomePresenter

/**
 * 首页model接口
 *
 * 持有 presenter的引用
 * @author neil
 * @date 2018/2/22
 */
interface HomeModel {

    fun getHomeList(onHomeListListener: HomePresenter.OnHomeListListener, page: Int = 0)

    fun cancelHomeListRequest()

    // banner
    fun getBanner(onBannerListener: HomePresenter.OnBannerListener)

    fun cancelBannerRequest()

}