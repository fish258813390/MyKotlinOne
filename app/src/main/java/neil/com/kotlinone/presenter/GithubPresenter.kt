package neil.com.kotlinone.presenter

import neil.com.kotlinone.bean.StarUser

/**
 *
 * @author neil
 * @date 2018/2/9
 */
interface GithubPresenter {

    /**
     * 获取star列表
     */
    fun getStarList()


    interface onGithubPresenter {

        /**
         * 获取成功
         */
        fun getStarSuccess(result: List<StarUser>)

        /**
         * 获取失败
         */
        fun getStarFail(message: String)
    }


}