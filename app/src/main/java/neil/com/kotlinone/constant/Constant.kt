package neil.com.kotlinone.constant

import android.widget.Toast

/**
 * 系统常量
 * @author neil
 * @date 2018/2/9
 */
object Constant {

    /**
     * baseUrl
     */
    val REQUEST_BASE_URL = "https://api.github.com"


    val REQUEST_CLIENT_BASE_URL = "http://wanandroid.com/"

    // const只能修饰val，不能修饰var
    // 经过const修饰的属性只能用拼接const修饰的属性去拼接
    const val SHARED_NAME = "_preferences"
    const val LOGIN_KEY = "login"
    const val USERNAME_KEY = "username"
    const val PASSWORD_KEY = "password"

    /**
     * result null
     */
    const val RESULT_NULL = "result null!"


    /**
     * url key
     */
    const val CONTENT_URL_KEY = "url"
    /**
     * title key
     */
    const val CONTENT_TITLE_KEY = "title"
    /**
     * id key
     */
    const val CONTENT_ID_KEY = "id"
    /**
     * share key
     */
    const val CONTENT_SHARE_TYPE = "text/plain"

    /**
     * cid key
     */
    const val CONTENT_CID_KEY = "cid"

    /**
     * childrenData key
     */
    const val CONTENT_CHILDREN_DATA_KEY = "childrenData"
    /**
     * target key
     */
    const val CONTENT_TARGET_KEY = "target"


    /**
     * Toast
     */
    @JvmField
    var showToast: Toast? = null


    const val SEARCH_KEY = "search"
    const val MAIN_REQUEST_CODE = 100
    const val MAIN_LIKE_REQUEST_CODE = 101

}