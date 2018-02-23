package neil.com.kotlinone.model

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import neil.com.kotlinone.presenter.GithubPresenter
import neil.com.kotlinone.utils.RetrofitUtils

/**
 *
 * @author neil
 * @date 2018/2/9
 */
class GithubModelImpl : GithubModel {

    var mGithubPresenter: GithubPresenter.onGithubPresenter? = null

    @SuppressLint("LongLogTag")
    override fun getStarList(githubPresenter: GithubPresenter.onGithubPresenter) {
        if (mGithubPresenter == null) {
            mGithubPresenter = githubPresenter
        }

        RetrofitUtils
                .retrofitService
                .getStarList()
                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            mGithubPresenter?.getStarSuccess(result)
                            Log.d("GithubModelImpl:success --->", result.toString())
                        },
                        { error ->
                            mGithubPresenter?.getStarFail(error.toString())
                            Log.d("GithubModelImpl:error --->", error.toString())
                        }, {
                    Log.d("GithubModelImpl --->", "onCompleted")
                }, {
                    Log.d("GithubModelImpl --->", "onStart")
                })

    }


}