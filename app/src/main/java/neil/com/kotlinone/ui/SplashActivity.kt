package neil.com.kotlinone.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import neil.com.kotlinone.MainActivity

/**
 * 闪屏ac
 * @author neil
 * @date 2018/2/22
 */
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
    }
}