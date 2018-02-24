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

    // 主页文章
    fun getHomeList(onHomeListListener: HomePresenter.OnHomeListListener, page: Int = 0)

    fun cancelHomeListRequest()

    // banner 广告
    fun getBanner(onBannerListener: HomePresenter.OnBannerListener)

    fun cancelBannerRequest()

    // 知识体系
    fun getTypeTreeList(onTypeTreeListListener: HomePresenter.OnTypeTreeListListener)

    fun cancelTypeTreeRequest()

}