package neil.com.kotlinone.view.client

import neil.com.kotlinone.bean.client.BannerResponse
import neil.com.kotlinone.bean.client.HomeListResponse

/**
 * 主页数据接口
 * @author neil
 * @date 2018/2/22
 */
interface HomeFragmentView {

    // 获取主页数据成功
    fun getHomeListSuccess(result: HomeListResponse)

    // 获取主页数据失败
    fun getHomeListFailed(errorMessage: String?)

    // 获取主页数据成功,条目为0
    fun getHomeListZero()

    // 获取主页数量小于20
    fun getHomeSmall(result: HomeListResponse)

    //
    fun getBannerSuccess(result: BannerResponse)

    fun getBannerFailed(errorMessage: String?)

    fun getBannerZero()

}
