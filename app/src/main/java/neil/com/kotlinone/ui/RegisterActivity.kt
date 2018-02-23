package neil.com.kotlinone.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import neil.com.kotlinone.R
import neil.com.kotlinone.api.GBService
import neil.com.kotlinone.bean.StarUser
import neil.com.kotlinone.presenter.GithubPresenter
import neil.com.kotlinone.presenter.GithubPresenterImpl
import neil.com.kotlinone.view.GithubView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * 注册
 * @author neil
 * @date 2018/2/8
 */
class RegisterActivity : AppCompatActivity(), View.OnClickListener, GithubView {

    var githubPresenter: GithubPresenter? = null

    var flag: String = ""

    var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        githubPresenter = GithubPresenterImpl(this)
        context = this
        btn_getdata.setOnClickListener(this)
        btn_getstar.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                flag = "2"
                GTThread().start()
            }
        })

        btn_rx_gedata.setOnClickListener(
                object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        toast("rxjav")
                        Log.d("rxjava", "start-----request")
                        githubPresenter?.getStarList()
                    }
                }
        )

        btn_jump.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent()
                intent.setClass(context, GithubStarActivity::class.java)
                startActivity(intent)
            }
        })

    }

    override fun onClick(v: View?) {
        val baseUrl = "http://blog.csdn.net/ww897532167/article/details/78327588"
        when (v?.id) {
            R.id.btn_getdata -> {
                HttpThread(baseUrl).start()
            }

        }

    }

    var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            if (msg?.what == 1) {
                tv_content.text = msg.toString()
            }
        }
    }

    inner class HttpThread : Thread {
        var baseUrl: String = ""

        constructor(baseUrl: String) : super(baseUrl) {
            this.baseUrl = baseUrl // 构造方法传值
        }

        override fun run() {
            super.run()
            val url = URL(baseUrl)
            var httpConnect = url.openConnection() as HttpURLConnection
            httpConnect.connectTimeout = 5 * 1000
            httpConnect.readTimeout = 5 * 1000
            httpConnect.doOutput = true
            httpConnect.doInput = true
            httpConnect.useCaches = false
            httpConnect.requestMethod = "POST"
            httpConnect.connect()
            var inputStream = httpConnect.inputStream
            var reader = BufferedReader(InputStreamReader(inputStream))
            Log.d("responseCode", httpConnect.responseCode.toString())
            var strBuilder = StringBuilder()
            reader.forEachLine {
                strBuilder.append(it)
                Log.e("TAG", it)
            }
            var message = Message.obtain()
            message.obj = strBuilder.toString()
            message.what = 1
            handler.sendMessage(message)
            // 关流
            inputStream.close()
            reader.close()
        }
    }

    inner class GTThread : Thread {

        constructor()

        override fun run() {
            super.run()
            GBService.githubService.getProjectStarList().execute().body()?.map {
                Log.d("StarUser----->", it.toString())
            }

        }
    }

    fun Activity.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }

    @SuppressLint("LongLogTag")
    override fun getStarListSuccess(result: List<StarUser>) {
        Log.d("rxjava---getStarListSuccess-->", result.toString())
    }

    @SuppressLint("LongLogTag")
    override fun getStarListFailed(message: String?) {
        Log.d("rxjava---getStarListFailed-->", message.toString())
    }


}
