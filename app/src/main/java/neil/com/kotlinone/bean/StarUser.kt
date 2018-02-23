package neil.com.kotlinone.bean

/**
 * Star 用户
 * @author neil
 * @date 2018/2/9
 */
data class StarUser(
        val login: String,
        val id: Long,
        val avatar_url: String,
        val gravatar_id: String?,
        val url: String,
        val html_url: String,
        val followers_url: String,
        val type: String,
        val site_admin: Boolean = false
)