package neil.com.kotlinone.bean.client

/**
 * 广告banner
 * @author neil
 * @date 2018/2/22
 */
data class BannerResponse(
        var errorCode: Int,
        var errorMsg: String?,
        var data: List<Data>?) {

    data class Data(
            var id: Int,
            var url: String,
            var imagePath:String,
            var title: String,
            var desc: String,
            var isVisible: Int,
            var order: Int,
            var `type`: Int
    )

}