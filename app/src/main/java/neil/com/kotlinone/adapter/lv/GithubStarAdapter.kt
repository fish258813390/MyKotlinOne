package neil.com.kotlinone.adapter.lv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import neil.com.kotlinone.R
import neil.com.kotlinone.bean.StarUser

/**
 *
 * @author neil
 * @date 2018/2/9
 */
class GithubStarAdapter(datas: List<StarUser>, context: Context?) : BaseAdapter() {

    var mDatas: List<StarUser>? = null
    var mInflater: LayoutInflater? = null
    var mContext: Context? = null

    init {
        mDatas = datas
        mContext = context
        mInflater = LayoutInflater.from(context)
    }

    fun setData(items: List<StarUser>) {
        this.mDatas = items
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder: ViewHolder
        var view: View

        if (convertView == null) {
            view = View.inflate(mContext, R.layout.lv_item_github_star, null)
            viewHolder = ViewHolder(view)
            view.setTag(viewHolder)
        } else {
            view = convertView
            viewHolder = view.getTag() as ViewHolder
        }
        viewHolder.tv_name.text = (mDatas?.get(position))?.login
        viewHolder.tv_url.text = (mDatas?.get(position))?.id.toString()
        return view
    }

    override fun getItem(position: Int): StarUser? {
        return mDatas?.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mDatas?.size ?: 0
    }

    class ViewHolder(var v: View) {
        var tv_name: TextView = v.findViewById(R.id.tv_name)
        var tv_url: TextView = v.findViewById(R.id.tv_url)
    }
}