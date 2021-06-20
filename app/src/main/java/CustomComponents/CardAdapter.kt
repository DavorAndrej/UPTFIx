package CustomComponents

import android.content.Context
import android.icu.util.ULocale
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class CardAdapter (var context: Context, var arrayList: MutableList<CategoryCard>) : BaseAdapter() {
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): CategoryCard {
        var view= arrayList.get(p0)
        return view
    }
    override fun getItem(positon: Int): CategoryCard {
        return arrayList.get(positon)
    }
    override fun getItemId(positon: Int): Long {
        return positon.toLong()
    }
    override fun getCount(): Int {
        return arrayList.size
    }


}