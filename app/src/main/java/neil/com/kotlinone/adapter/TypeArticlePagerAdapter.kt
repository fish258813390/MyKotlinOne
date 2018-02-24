package neil.com.kotlinone.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.util.Log
import neil.com.kotlinone.bean.client.TreeListResponse
import neil.com.kotlinone.ui.fragment.TypeArticleFragment

/**
 * 文章类型adapter viewpager
 * @author neil
 * @date 2018/2/24
 */
class TypeArticlePagerAdapter(
        val list: List<TreeListResponse.Data.Children>, fm: FragmentManager) :
        FragmentPagerAdapter(fm) {

    private val listFragment = mutableListOf<Fragment>()

    init {
        list.forEach {
            Log.d("TypeAdapter", it.name + "::" + it.id)
            listFragment.add(TypeArticleFragment.newInstance(it.id))
        }
    }

    override fun getItem(position: Int): Fragment = listFragment[position]

    override fun getCount(): Int = list.size

    override fun getPageTitle(position: Int): CharSequence = list[position].name

    override fun getItemPosition(`object`: Any?): Int = PagerAdapter.POSITION_NONE

}
