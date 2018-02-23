package neil.com.kotlinone.model

import neil.com.kotlinone.presenter.GithubPresenter

/**
 *
 * @author neil
 * @date 2018/2/9
 */
interface GithubModel {

    /**
     * 获取star list
     */
    fun getStarList(githubPresenter: GithubPresenter.onGithubPresenter)

}