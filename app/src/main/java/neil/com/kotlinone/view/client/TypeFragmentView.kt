package neil.com.kotlinone.view.client

import neil.com.kotlinone.bean.client.TreeListResponse

/**
 * 文章类型
 * @author neil
 * @date 2018/2/24
 */
interface TypeFragmentView {

    fun getTypeListSuccess(result: TreeListResponse)

    fun getTypeListFail(errorMessage: String?)

    fun getTypeListZero()

}