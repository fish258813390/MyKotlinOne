package neil.com.kotlinone.ui.activity

import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_content.*
import kotlinx.android.synthetic.main.content_about.*
import neil.com.kotlinone.R
import neil.com.kotlinone.base.BaseActivity

/**
 * 关于我们
 * @author neil
 * @date 2018/2/24
 */
class AboutActivity : BaseActivity() {

    override fun setLayoutId(): Int = R.layout.activity_about

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar.titleBar(R.id.toolbar).init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.run {
            title = getString(R.string.my_about)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        aboutContent.text = "关于我们"
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            // toolbar 返回
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun cancelRequest() {
    }

}