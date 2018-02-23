package neil.com.kotlinone.view

import neil.com.kotlinone.bean.StarUser

/**
 * github 访问 回调
 * @author neil
 * @date 2018/2/9
 */
interface GithubView {

    /**
     * 获取成功
     */
    fun getStarListSuccess(result: List<StarUser>)

    /**
     * 登录失败
     */
    fun getStarListFailed(message: String?)


}