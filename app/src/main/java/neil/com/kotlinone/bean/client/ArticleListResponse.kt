package neil.com.kotlinone.bean.client

/**
 * 文章类型bean
 * @author neil
 * @date 2018/2/24
 */
data class ArticleListResponse(
        var errorCode: Int,
        var errorMsg: String?,
        var data: Data
)
