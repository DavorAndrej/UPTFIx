package CustomComponents

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class ReportedProblemCardAdapter(var context: Context, var arrayList: MutableList<ReportedProblemCard>) : BaseAdapter() {
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): ReportedProblemCard {
        var view= arrayList.get(p0)
        return view
    }
    override fun getItem(positon: Int): ReportedProblemCard {
        return arrayList.get(positon)
    }
    override fun getItemId(positon: Int): Long {
        return positon.toLong()
    }
    override fun getCount(): Int {
        return arrayList.size
    }


}