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

    override fun getItemCount(): Int {
        return mData?.size ?: 0
    }


}