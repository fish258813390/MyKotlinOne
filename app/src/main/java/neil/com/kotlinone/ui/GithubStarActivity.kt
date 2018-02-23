package neil.com.kotlinone.ui

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_common_recyclerview.*
import neil.com.kotlinone.R
import neil.com.kotlinone.adapter.lv.GithubStarAdapter
import neil.com.kotlinone.adapter.rv.GithubStarRvAdapter
import neil.com.kotlinone.bean.StarUser
import neil.com.kotlinone.presenter.GithubPresenter
import neil.com.kotlinone.presenter.GithubPresenterImpl
import neil.com.kotlinone.view.GithubView

/**
 *
 * @author neil
 * @date 2018/2/9
 */
class GithubStarActivity : AppCompatActivity(), GithubView {

    // lateinit关键字表示延时初始化
    lateinit var lvAdapter: GithubStarAdapter
    lateinit var rvAdapter: GithubStarRvAdapter
    lateinit var mDatas: List<StarUser>

    var githubPresenter: GithubPresenter? = null
    var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_github_star)
        setContentView(R.layout.activity_common_recyclerview)

        mContext = this
        githubPresenter = GithubPresenterImpl(this)
        githubPresenter?.getStarList()
    }

    override fun getStarListSuccess(result: List<StarUser>) {
        Log.d("success-->", result.toString())

        // listView
//        lvAdapter = GithubStarAdapter(result, context)
//        lv_github_star.adapter = lvAdapter

        // recyclerView
        rvAdapter = GithubStarRvAdapter(result, mContext)
        rv_common.layoutManager = LinearLayoutManager(this)
        rv_common.adapter = rvAdapter
        rvAdapter.setOnclickListener(object : GithubStarRvAdapter.MyInter {
            override fun testCallback(str: String) {
                Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun getStarListFailed(message: String?) {
    }

}