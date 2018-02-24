package neil.com.kotlinone.ui

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.AppCompatButton
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_home_client_main.*
import neil.com.kotlinone.MainActivity
import neil.com.kotlinone.R
import neil.com.kotlinone.base.BaseActivity
import neil.com.kotlinone.base.Preference
import neil.com.kotlinone.constant.Constant
import neil.com.kotlinone.toast
import neil.com.kotlinone.ui.activity.AboutActivity
import neil.com.kotlinone.ui.fragment.CommonUseFragment
import neil.com.kotlinone.ui.fragment.HomeFragment
import neil.com.kotlinone.ui.fragment.TypeFragment

/**
 * clientMain
 * @author neil
 * @date 2018/2/22
 */
class HomeClientActivity : BaseActivity() {

    private var lastTime: Long = 0

    private var currentIndex = 0

    private var homeFragment: HomeFragment? = null
    private var typeFragment: TypeFragment? = null
    private var commonUseFragment: CommonUseFragment? = null
    private val fragmentMannager by lazy {
        supportFragmentManager
    }

    private val isLogin: Boolean by Preference(Constant.LOGIN_KEY, false)
    private val username: String by Preference(Constant.USERNAME_KEY, "")

    private lateinit var navigationViewUsername: TextView

    private lateinit var navigationViewLogout: AppCompatButton

    override fun setLayoutId(): Int = R.layout.activity_home_client_main

    override fun initImmersionBar() {
        super.initImmersionBar()
        immersionBar.titleBar(R.id.toolbar).init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // titleBar
        toolbar.run {
            title = getString(R.string.app_name)
            setSupportActionBar(this)
        }
        bottomNavigation.run {
            setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
            selectedItemId = R.id.navigation_home
        }
        drawerLayout.run {
            val toggle = ActionBarDrawerToggle(
                    this@HomeClientActivity,
                    this,
                    toolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close
            )
            addDrawerListener(toggle)
            toggle.syncState()
        }
        navigationView.run {
            setNavigationItemSelectedListener(onDrawerNavigationItemSelectedListener)
        }
        navigationViewUsername =
                navigationView.getHeaderView(0).findViewById<TextView>(R.id.navigationViewUsername)
        navigationViewLogout =
                navigationView.getHeaderView(0).findViewById<AppCompatButton>(R.id.navigationViewLogout)
        navigationViewUsername.run {
            if (!isLogin) {
                text = getString(R.string.not_login)
            } else {
                text = username
            }
        }

        navigationViewLogout.run {
            text = if (!isLogin) {
                getString(R.string.goto_login)
            } else {
                getString(R.string.logout)
            }
            setOnClickListener {
                if (!isLogin) {
                    Intent(this@HomeClientActivity, MainActivity::class.java).run {
                        startActivityForResult(this, Constant.MAIN_REQUEST_CODE)
                    }
                } else {
                    Preference.clear()
                    navigationViewUsername.text = getString(R.string.not_login)
                    text = getString(R.string.goto_login)
                    homeFragment?.refreshData()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menuSearch) {
            Intent(this, MainActivity::class.java).run {
                startActivity(this) // this 只带当前intent对象
            }
            return true
        }
        when (item?.itemId) {
            R.id.menuSearch -> {
                Intent(this, MainActivity::class.java).run {
                    startActivity(this)
                }
                return true
            }
            R.id.menuHot -> {
                if (currentIndex == R.id.menuHot) {
//                    commonUseFragment?.refreshData()
                }
                setFragment(R.id.menuHot)
                currentIndex = R.id.menuHot
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private val onDrawerNavigationItemSelectedListener = NavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_like -> {
                if (!isLogin) {
//                    Intent(this, MainActivity::class.java).run {
//                        startActivityForResult(this, 1)
//                    }
                    toast(getString(R.string.login_please_login))
                    return@OnNavigationItemSelectedListener true
                }
//                Intent(this, MainActivity::class.java).run {
//                    putExtra(Constant.SEARCH_KEY, false)
//                    startActivityForResult(this, Constant.MAIN_LIKE_REQUEST_CODE)
//                }
            }
            R.id.nav_about -> {
                Intent(this, AboutActivity::class.java).run {
                    startActivity(this)
                }
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        true
    }

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)
        when (fragment) {
            is HomeFragment -> {
                homeFragment ?: let {
                    homeFragment = fragment
                }
            }
            is TypeFragment -> {
                typeFragment?.let {
                    typeFragment = fragment
                }
            }
            is CommonUseFragment -> {
                commonUseFragment ?: let {
                    commonUseFragment = fragment
                }
            }
        }
    }

    // 导航监听
    private val onNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                setFragment(item.itemId)
                return@OnNavigationItemSelectedListener when (item.itemId) {
                    R.id.navigation_home -> {
                        if (currentIndex == R.id.navigation_home) {
                            homeFragment?.smoothScrollToPosition()
                        }
                        currentIndex = R.id.navigation_home
                        true
                    }
                    R.id.navigation_type -> {
                        if (currentIndex == R.id.navigation_type) {
                            typeFragment?.smoothScrollToPosition()
                        }
                        currentIndex = R.id.navigation_type
                        true
                    }
                    else -> {
                        false
                    }
                }
            }


    // 显示对应的Fragment
    private fun setFragment(index: Int) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        fragmentMannager.beginTransaction().apply {
            homeFragment ?: let {
                HomeFragment().let {
                    homeFragment = it
                    add(R.id.content, it)
                }
            }
            typeFragment ?: let {
                TypeFragment().let {
                    typeFragment = it
                    add(R.id.content, it)
                }
            }
            commonUseFragment ?: let {
                CommonUseFragment().let {
                    commonUseFragment = it
                    add(R.id.content, it)
                }
            }
            // 隐藏fragment
            hideFragment(this)
            // 根据index 显示fragment
            when (index) {
                R.id.navigation_home -> {
                    toolbar.title = getString(R.string.app_name)
                    homeFragment.let {
                        this.show(it)
                    }
                }
                R.id.navigation_type -> {
                    toolbar.title = getString(R.string.title_dashboard)
                    typeFragment.let {
                        this.show(it)
                    }
                }
                R.id.menuHot -> {
                    toolbar.title = getString(R.string.hot_title)
                    commonUseFragment.let {
                        this.show(it)
                    }
                }
            }

        }.commit()
    }

    // 隐藏所有的fragment
    private fun hideFragment(transaction: FragmentTransaction) {
        homeFragment.let {
            transaction.hide(it)
        }
        typeFragment?.let {
            transaction.hide(it)
        }
        commonUseFragment.let {
            transaction.hide(it)
        }
    }

    override fun cancelRequest() {

    }

}