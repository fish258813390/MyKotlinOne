package neil.com.kotlinone.presenter.impl

import neil.com.kotlinone.bean.client.ArticleListResponse
import neil.com.kotlinone.model.TypeArticleModel
import neil.com.kotlinone.model.impl.TypeArticleModelImpl
import neil.com.kotlinone.presenter.client.TypeArticlePresenter
import neil.com.kotlinone.view.client.CollectArticleView
import neil.com.kotlinone.view.client.TypeArticleFragmentView

/**
 *
 * @author neil
 * @date 2018/2/24
 */
class TypeArticlePresenterImpl(
        val typeArticleFragmentView: TypeArticleFragmentView,
        val collectArticleView: CollectArticleView
) : TypeArticlePresenter, TypeArticlePresenter.OnTypeArticleListListener {

    private val typeArticleModel: TypeArticleModel = TypeArticleModelImpl()

    override fun getTypeArticleList(page: Int, cid: Int) {
        typeArticleModel.getTypeArticleList(this, page, cid)
    }

    override fun getTypeArticleListSuccess(result: ArticleListResponse) {
        if (result.errorCode != 0) {
            typeArticleFragmentView.getTypeArticleListFailed(result.errorMsg)
            return
        }
        val total = result.data.total

        when {
            total == 0 -> {
                typeArticleFragmentView.getTypeArticleListZero()
                return
            }
            total < result.data.size -> {
                typeArticleFragmentView.getTypeArticleListSmall(result)
                return
            }
            else ->
                typeArticleFragmentView.getTypeArticleListSuccess(result)
        }
    }

    override fun getTypeArticleListFailed(errorMessage: String?) {
        typeArticleFragmentView.getTypeArticleListFailed(errorMessage)
    }

    fun cancelRequest(){
        typeArticleModel.cancelRequest()
    }

}