package neil.com.kotlinone.adapter.rv

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import neil.com.kotlinone.R
import neil.com.kotlinone.bean.client.Datas

/**
 *
 * @author neil
 * @date 2018/2/24
 */
class TypeArticleAdapter(val context: Context, datas: MutableList<Datas>) :
        BaseQuickAdapter<Datas, BaseViewHolder>(R.layout.home_list_item, datas) {
    override fun convert(helper: BaseViewHolder, item: Datas?) {
        item ?: return
        @Suppress("DEPRECATION")
        helper.setText(R.id.homeItemTitle, item.title)
                .setText(R.id.homeItemAuthor, item.author)
                .setVisible(R.id.homeItemType, false)
                .setText(R.id.homeItemDate, item.niceDate)
                .setImageResource(
                        R.id.homeItemLike,
                        if (item.collect) R.drawable.ic_action_like else R.drawable.ic_action_no_like
                )
                .addOnClickListener(R.id.homeItemLike)
    }
}