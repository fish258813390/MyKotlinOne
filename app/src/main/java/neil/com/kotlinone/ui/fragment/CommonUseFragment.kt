package neil.com.kotlinone.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import neil.com.kotlinone.R
import neil.com.kotlinone.base.BaseFragment

/**
 *
 * @author neil
 * @date 2018/2/23
 */
class CommonUseFragment : BaseFragment() {

    private var mainView: View? = null

    override fun cancelRequest() {
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainView = inflater?.inflate(R.layout.fragment_commonuse, container, false)
        return mainView
    }

}