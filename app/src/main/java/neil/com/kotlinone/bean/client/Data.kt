package neil.com.kotlinone.bean.client

/**
 *
 * @author neil
 * @date 2018/2/22
 */
data class Data(
        var offset: Int,
        var size: Int,
        var total: Int,
        var pageCount: Int,
        var curPage: Int,
        var over: Boolean,
        var datas: List<Datas>?
)