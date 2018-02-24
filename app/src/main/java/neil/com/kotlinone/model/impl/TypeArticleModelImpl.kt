package neil.com.kotlinone.model.impl

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import neil.com.kotlinone.constant.Constant
import neil.com.kotlinone.model.TypeArticleModel
import neil.com.kotlinone.presenter.client.TypeArticlePresenter
import neil.com.kotlinone.utils.RetrofitUtils

/**
 *
 * @author neil
 * @date 2018/2/24
 */
class TypeArticleModelImpl : TypeArticleModel {

    override fun getTypeArticleList(onTypeArticleListListener: TypeArticlePresenter.OnTypeArticleListListener, page: Int, cid: Int) {
        RetrofitUtils
                .retrofitClientService
                .getArticleList(page, cid)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            onTypeArticleListListener.getTypeArticleListSuccess(result)
                            Log.e("TypeArticleSuccess --->", result.toString())
                        },
                        { error ->
                            onTypeArticleListListener.getTypeArticleListFailed(Constant.RESULT_NULL)
                            Log.d("TypeArticleError --->", error.toString())
                        }, {
                    Log.d("TypeModelImpl --->", "onCompleted")
                }, {
                    Log.d("TypeModelImpl --->", "onStart")
                })

    }

    override fun cancelRequest() {

    }

}