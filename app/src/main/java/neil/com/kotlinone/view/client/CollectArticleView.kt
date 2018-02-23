package neil.com.kotlinone.view.client

import neil.com.kotlinone.bean.client.HomeListResponse

/**
 * 我的文章收集
 * @author neil
 * @date 2018/2/22
 */
interface CollectArticleView {

    // 添加到我的文章
    fun collectArticleSuccess(result: HomeListResponse, isAdd: Boolean)

    // 收藏失败
    fun collectArticleFailed(errorMessage: String?, isAdd: Boolean)

}



