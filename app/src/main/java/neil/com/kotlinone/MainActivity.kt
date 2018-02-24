package neil.com.kotlinone

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import neil.com.kotlinone.ui.HomeClientActivity
import neil.com.kotlinone.ui.RegisterActivity
import neil.com.kotlinone.ui.activity.AboutActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_register.setOnClickListener(this)
        btn_login.setOnClickListener(this)
        btn_client.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_register ->
                toRegister()
            R.id.btn_login -> {
                Intent(this,AboutActivity::class.java).run {
                    startActivity(this)
                }
            }
            R.id.btn_client -> {
                Intent(this, HomeClientActivity::class.java).run {
                    startActivity(this)
                }
            }
        }
    }

    fun toRegister() {
        val intent = Intent()
        intent.setClass(this, RegisterActivity::class.java)
        startActivity(intent)
    }

}
