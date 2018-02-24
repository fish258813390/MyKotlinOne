package neil.com.kotlinone.presenter.client

import neil.com.kotlinone.bean.client.ArticleListResponse

/**
 *
 * @author neil
 * @date 2018/2/24
 */
interface TypeArticlePresenter {

    fun getTypeArticleList(page: Int = 0, cid: Int)

    interface OnTypeArticleListListener {

        fun getTypeArticleListSuccess(result: ArticleListResponse)

        fun getTypeArticleListFailed(errorMessage: String?)
    }

}