package neil.com.kotlinone.ui.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_type_content.*
import neil.com.kotlinone.R
import neil.com.kotlinone.adapter.TypeArticlePagerAdapter
import neil.com.kotlinone.base.BaseActivity
import neil.com.kotlinone.bean.client.TreeListResponse
import neil.com.kotlinone.constant.Constant

/**
 * 类型内容
 * @author neil
 * @date 2018/2/24
 */
class TypeContentActivity : BaseActivity() {

    private lateinit var firstTitle: String

    private val list = mutableListOf<TreeListResponse.Data.Children>()

    private var target: Boolean = false

    private val typeArticlePagerAdapter: TypeArticlePagerAdapter by lazy {
        TypeArticlePagerAdapter(list, supportFragmentManager)
    }

    override fun setLayoutId(): Int = R.layout.activity_type_content

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar.titleBar(R.id.typeSecondToolbar).init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        typeSecondToolbar.run {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        intent.extras?.let { extras ->
            target = extras.getBoolean(Constant.CONTENT_TARGET_KEY, false)
            extras.getString(Constant.CONTENT_TITLE_KEY)?.let {
                firstTitle = it
                typeSecondToolbar.title = it
            }
            if (target) {
                list.add(
                        TreeListResponse.Data.Children(
                                extras.getInt(Constant.CONTENT_CID_KEY, 0),
                                firstTitle, 0, 0, 0, 0, null
                        )
                )
            } else {
                extras.getSerializable(Constant.CONTENT_CHILDREN_DATA_KEY)?.let {
                    val data = it as TreeListResponse.Data
                    data.children?.let { children ->
                        list.addAll(children)
                    }
                }
            }
        }
        typeSecondViewPager.run {
            adapter = typeArticlePagerAdapter
        }
        typeSecondTabs.run {
            setupWithViewPager(typeSecondViewPager)
        }

        typeSecondViewPager.addOnPageChangeListener(
                TabLayout.TabLayoutOnPageChangeListener(typeSecondTabs)
        )
        typeSecondTabs.addOnTabSelectedListener(
                TabLayout.ViewPagerOnTabSelectedListener(typeSecondViewPager)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_type_content, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home ->{
                finish()
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }



    override fun cancelRequest() {
    }
}