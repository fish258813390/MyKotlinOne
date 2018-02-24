package neil.com.kotlinone.model.impl

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import neil.com.kotlinone.bean.client.BannerResponse
import neil.com.kotlinone.bean.client.HomeListResponse
import neil.com.kotlinone.cancelByActive
import neil.com.kotlinone.constant.Constant
import neil.com.kotlinone.model.HomeModel
import neil.com.kotlinone.presenter.client.HomePresenter
import neil.com.kotlinone.utils.RetrofitUtils

/**
 *
 * @author neil
 * @date 2018/2/22
 */
class HomeModelImpl : HomeModel {

    // 协程任务 https://www.jianshu.com/p/d4a8358e843e
    private var homeListAsync: Deferred<HomeListResponse>? = null

    // 获取banner
    private var bannerAsync: Deferred<BannerResponse>? = null


    // 获取文章详情
    override fun getHomeList(onHomeListListener: HomePresenter.OnHomeListListener, page: Int) {
//        async(UI) {
//            homeListAsync?.cancelByActive()
//            homeListAsync = RetrofitUtils.retrofitClientService.getHomeList(page)
//            val result = homeListAsync?.await()
//            result ?: let {
//                onHomeListListener.getHomeListFailed(Constant.RESULT_NULL)
//                return@async
//            }
//            onHomeListListener.getHomeListSuccess(result)
//        }

        RetrofitUtils
                .retrofitClientService
                .getHomeList1(page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            onHomeListListener.getHomeListSuccess(result)
                            Log.e("HomeListSuccess --->", result.toString())
                        },
                        { error ->
                            onHomeListListener.getHomeListFailed(Constant.RESULT_NULL)
                            Log.d("HomeListError --->", error.toString())
                        }, {
                    Log.d("HomeModelImpl --->", "onCompleted")
                }, {
                    Log.d("HomeModelImpl --->", "onStart")
                })

    }

    // 取消文章请求
    override fun cancelHomeListRequest() {
        homeListAsync?.cancelByActive()
    }

    override fun getBanner(onBannerListener: HomePresenter.OnBannerListener) {
//        async(UI) {
//            bannerAsync?.cancelByActive()
//            bannerAsync = RetrofitUtils.coroutineService.getBanner()
//            val result = bannerAsync?.await()
//            result ?: let {
//                onBannerListener.getBannerFailed(Constant.RESULT_NULL)
//                return@async
//            }
//            onBannerListener.getBannerSuccess(result)
//        }

        RetrofitUtils
                .retrofitClientService
                .getBanner1()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            onBannerListener.getBannerSuccess(result)
                            Log.d("success --->", result.toString())
                        },
                        { error ->
                            onBannerListener.getBannerFailed(Constant.RESULT_NULL)
                            Log.d("error --->", error.toString())
                        }, {
                    Log.d("HomeModelImpl --->", "onCompleted")
                }, {
                    Log.d("HomeModelImpl --->", "onStart")
                })

    }

    override fun cancelBannerRequest() {
        bannerAsync?.cancel()
    }

}