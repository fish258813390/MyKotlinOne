package neil.com.kotlinone.base

import android.support.v4.app.Fragment


/**
 * fragment 基类
 * @author neil
 * @date 2018/2/22
 */
abstract class BaseFragment : Fragment() {

    protected var isFirst: Boolean = true // ? 区分懒加载?

    override fun onDestroyView() {
        super.onDestroyView()
        // 取消网络请求,防止内存泄漏
        cancelRequest()
    }

    protected abstract fun cancelRequest()

}