package neil.com.kotlinone.adapter.rv

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import neil.com.kotlinone.R
import neil.com.kotlinone.bean.client.BannerResponse

/**
 * 广告banner 适配器
 * @author neil
 * @date 2018/2/22
 */
class BannerAdapter(val context: Context, datas: MutableList<BannerResponse.Data>)
    : BaseQuickAdapter<BannerResponse.Data, BaseViewHolder>(R.layout.rv_banner_item, datas) {

    override fun convert(helper: BaseViewHolder?, item: BannerResponse.Data?) {
        helper?.setText(R.id.bannerTitle, item?.title?.trim())
        val imageView = helper?.getView<ImageView>(R.id.bannerImage)
        Glide.with(context).load(item?.imagePath).placeholder(R.mipmap.ic_launcher).into(imageView)
    }


}