package neil.com.kotlinone.view.client

import neil.com.kotlinone.bean.client.ArticleListResponse

/**
 * 文章类型view
 * @author neil
 * @date 2018/2/24
 */
interface TypeArticleFragmentView{


    fun getTypeArticleListSuccess(result: ArticleListResponse)


    fun getTypeArticleListFailed(errorMessage: String?)


    fun getTypeArticleListZero()


    fun getTypeArticleListSmall(result: ArticleListResponse)
}