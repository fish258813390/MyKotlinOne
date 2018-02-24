package neil.com.kotlinone.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import com.gyf.barlibrary.ImmersionBar

/**
 * Activity 基类
 *
 * let 方法
 *
 * @author neil
 * @date 2018/2/22
 */
abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var immersionBar: ImmersionBar

    private val imm: InputMethodManager by lazy {
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayoutId())
        initImmersionBar()
    }

    abstract fun setLayoutId(): Int

    open protected fun initImmersionBar() {
        // 在 BaseActivity 里初始化
        immersionBar = ImmersionBar.with(this)
        immersionBar.init()
    }

    // 取消网络请求
    protected abstract fun cancelRequest()


    override fun onDestroy() {
        super.onDestroy()
        // 必须调用改方法,防止内存泄漏。如果不调用改方法，当界面bar发生变化,
        // 在不关闭app的情况下，退出此页面再进入页面时,会记录上一个状态
        immersionBar.destroy()
        cancelRequest()
    }

    override fun finish() {
        if (!isFinishing) {
            super.finish()
            hideSoftKeyBoard()
        }
    }

    private fun hideSoftKeyBoard() {
        // 调用某对象的let函数，则该对象为函数的参数。在函数块内可以通过 it 指代该对象。
        // 返回值为函数块的最后一行或指定return表达式。
        currentFocus?.let {
            imm.hideSoftInputFromWindow(it.windowToken, 2)
        }
    }

}