package pl.valueadd.restcountries.presentation.main.countries.list.item

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.listeners.ClickEventHook
import pl.valueadd.restcountries.domain.model.country.CountryModel

class ClickCountryItemEventHook(private val listener: Listener) : ClickEventHook<IItem<*>>() {

    override fun onBind(viewHolder: RecyclerView.ViewHolder): View? {
        return if (viewHolder is CountryItem.ViewHolder) {
            viewHolder.itemView
        } else super.onBind(viewHolder)
    }

    override fun onClick(v: View, position: Int, fastAdapter: FastAdapter<IItem<*>>, item: IItem<*>) {
        (item as? CountryItem)?.let {
            listener.onCountryItemClick(it.model)
        }
    }

    interface Listener {

        fun onCountryItemClick(model: CountryModel)
    }
}