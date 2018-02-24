package neil.com.kotlinone.adapter.rv

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import neil.com.kotlinone.R
import neil.com.kotlinone.bean.client.TreeListResponse

/**
 * {
"children": [
{
"children": [],
"courseId": 13,
"id": 60,
"name": "Android Studio相关",
"order": 1000,
"parentChapterId": 150,
"visible": 1
},
{
"children": [],
"courseId": 13,
"id": 169,
"name": "gradle",
"order": 1001,
"parentChapterId": 150,
"visible": 1
},
{
"children": [],
"courseId": 13,
"id": 269,
"name": "官方发布",
"order": 1002,
"parentChapterId": 150,
"visible": 1
}
],
"courseId": 13,
"id": 150,
"name": "开发环境",
"order": 1,
"parentChapterId": 0,
"visible": 1
}
 * @author neil
 * @date 2018/2/24
 */
class TypeAdapter(val context: Context, datas: MutableList<TreeListResponse.Data>) : BaseQuickAdapter<TreeListResponse.Data, BaseViewHolder>(
        R.layout.type_list_item, datas) {

    override fun convert(helper: BaseViewHolder?, item: TreeListResponse.Data?) {
        item ?: return
        helper?.setText(R.id.typeItemFirst, item.name)
        item.children?.let { children ->
            helper?.setText(
                    R.id.typeItemSecond,
                    children.joinToString("     ", transform = { child ->
                        child.name
                    })
            )
        }
    }

}