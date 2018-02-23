package neil.com.kotlinone.adapter.rv

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import neil.com.kotlinone.R
import neil.com.kotlinone.bean.StarUser

/**
 * recyclerAdapter
 * with 函数
 * 接收一个对象和一个扩展函数作为它的参数，然后使这个对象扩展这个函数。
 * 这表示所有在括号中编写的代码都是作为对象(第一个参数)的一个扩展函数，
 * 我们就可以像作为this样使用它的public方法和属性
 *
 * kotlin 中 lambdas表达式 的应用是，把一个函数作为另一个函数的参数
 * 一个lambda表达式通过参数的形式被定义在箭头的左边(被圆括号包围),然后在箭头的右边返回结果值
 * eg： view的点击事件，我们接收一个View，然后返回一个Unit(没有值)
 * 包含单个函数可以被替换成另一个函数
 *
 * 当我们定义了一个方法，我们必须使用大括号包围,然后在箭头的左边制定参数在箭头的右边返回函数执行的
 * 结果。
 * 1.如果左边的参数没有使用到,甚至可以省略左边的参数。
 * 2.如果这个函数的最后一个参数是一个函数,我们可以把这个函数移动到圆括号外边.
 * 3.如果这个函数只有一个参数，我们可以省略这个圆括号
 *
 * @author neil
 * @date 2018/2/9
 */
class GithubStarRvAdapter(datas: List<StarUser>, context: Context?) : RecyclerView.Adapter<GithubStarRvAdapter.MyViewHolder>() {

    var mData: List<StarUser>? = null
    var mContext: Context? = null
    var mInter: MyInter? = null

    init {
        mData = datas
        mContext = context
    }

    fun setOnclickListener(inter: MyInter) {
        mInter = inter
    }

    // 利用lambdas表达式
//    fun setOnLongClickListenre(listener:)

    interface MyInter {
        // 定义的回调借口
        fun testCallback(str: String)
    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        holder?.name?.text = (mData?.get(position))?.login
        holder?.url?.text = (mData?.get(position))?.id.toString()

        holder?.name?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (mInter != null) {
                    mInter?.testCallback("hello, 我是第 " + (position + 1) + "个")
                }
            }
        })
        // 利用lambdas表达式
        holder?.name?.setOnClickListener { v -> ""  }


        // with 函数
        // 接收一个对象和一个扩展函数作为它的参数，然后使这个对象扩展这个函数。
        // 这表示所有在括号中编写的代码都是作为对象(第一个参数)的一个扩展函数，
        // 我们就可以像作为this样使用它的public方法和属性
        with(mData?.get(position)) {
            holder?.name?.text = "${this?.id} - ${this?.login}"
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        var view: View = LayoutInflater.from(mContext).inflate(R.layout.lv_item_github_star, null)
        var holder = MyViewHolder(view)
        return holder
    }

    class MyViewHolder : RecyclerView.ViewHolder {
        var name: TextView? = null
        var url: TextView? = null

        constructor(view: View) : super(view) {
            name = view.findViewById(R.id.tv_name)
            url = view.findViewById(R.id.tv_url)
        }

    }

//    override fun getItemCount(): Int {
//        return mData?.size ?: 0
//    }

    override fun getItemCount(): Int = mData?.size ?: 0

}