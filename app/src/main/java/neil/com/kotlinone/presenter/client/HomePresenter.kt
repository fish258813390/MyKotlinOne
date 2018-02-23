package neil.com.kotlinone.presenter.client

import neil.com.kotlinone.bean.client.BannerResponse
import neil.com.kotlinone.bean.client.HomeListResponse

/**
 * 首页
 * @author neil
 * @date 2018/2/22
 */
interface HomePresenter {


    // 首页文章
    interface OnHomeListListener {

        fun getHomeList(page: Int = 0)

        fun getHomeListSuccess(result: HomeListResponse)

        fun getHomeListFailed(errorMessage: String)

    }

    // 广告banner
    interface OnBannerListener {

        fun getBanner()

        fun getBannerSuccess(result: BannerResponse)

        fun getBannerFailed(errorMessage: String?)
    }


}
