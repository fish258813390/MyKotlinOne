package neil.com.kotlinone.model

import neil.com.kotlinone.presenter.client.TypeArticlePresenter

/**
 *
 * @author neil
 * @date 2018/2/24
 */
interface TypeArticleModel {

    fun getTypeArticleList(
            onTypeArticleListListener: TypeArticlePresenter.OnTypeArticleListListener,
            page: Int = 0,
            cid: Int
    )

    fun cancelRequest()

}