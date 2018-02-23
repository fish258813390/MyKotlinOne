package neil.com.kotlinone.presenter

import neil.com.kotlinone.bean.StarUser
import neil.com.kotlinone.model.GithubModel
import neil.com.kotlinone.model.GithubModelImpl
import neil.com.kotlinone.view.GithubView

/**
 * Github
 * @author neil
 * @date 2018/2/9
 */
class GithubPresenterImpl(val githubView: GithubView) : GithubPresenter, GithubPresenter.onGithubPresenter {

    val mGithubModel: GithubModel

    init {
        mGithubModel = GithubModelImpl()
    }

    override fun getStarList() {
        mGithubModel.getStarList(this)
    }

    override fun getStarSuccess(result: List<StarUser>) {
        githubView.getStarListSuccess(result)
    }

    override fun getStarFail(message: String) {
        githubView.getStarListFailed(message)
    }

}