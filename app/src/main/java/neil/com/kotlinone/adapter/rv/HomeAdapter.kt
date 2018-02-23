package neil.com.kotlinone.adapter.rv

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import neil.com.kotlinone.R
import neil.com.kotlinone.bean.client.Datas

/**
 *
 * @author neil
 * @date 2018/2/22
 */
class HomeAdapter(val context: Context, datas: MutableList<Datas>)
    : BaseQuickAdapter<Datas, BaseViewHolder>(R.layout.rv_home_list_item, datas) {

    override fun convert(helper: BaseViewHolder?, item: Datas?) {
//        item ?: return
        if (item != null) {
            helper?.setText(R.id.homeItemTitle, item?.title)
                    ?.setText(R.id.homeItemAuthor, item?.author)
                    ?.setText(R.id.homeItemType, item?.chapterName)
                    ?.addOnClickListener(R.id.homeItemType)
                    ?.setTextColor(R.id.homeItemType, context.resources.getColor(R.color.colorPrimary))
                    ?.linkify(R.id.homeItemType)
                    ?.setText(R.id.homeItemDate, item?.niceDate)
                    ?.setImageResource(
                            R.id.homeItemLike,
                            if (item.collect) {
                                R.drawable.ic_action_like
                            } else {
                                R.drawable.ic_action_no_like
                            }
                    )
                    ?.addOnClickListener(R.id.homeItemLike)
        }


    }

}