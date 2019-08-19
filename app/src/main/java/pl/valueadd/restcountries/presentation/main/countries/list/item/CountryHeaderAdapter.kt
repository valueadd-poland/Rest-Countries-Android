package pl.valueadd.restcountries.presentation.main.countries.list.item

import android.view.View
import com.mikepenz.fastadapter.GenericItem
import kotlinx.android.synthetic.main.country_item_header.view.countryHeaderTextView
import org.apache.commons.lang3.math.NumberUtils.LONG_MINUS_ONE
import pl.valueadd.restcountries.R
import pl.valueadd.restcountries.view.recyclerview.StickyHeaderAdapter

class CountryHeaderAdapter : StickyHeaderAdapter<GenericItem>(R.layout.country_item_header) {

    override fun getHeaderId(item: GenericItem, itemType: Int): Long {
        return if (item is CountryItem) {
            item.model.name.first().toLong()
        } else LONG_MINUS_ONE
    }

    override fun onBindView(item: GenericItem, itemView: View) = itemView.run {
        if (item is CountryItem) {
            countryHeaderTextView.text = item.model.name
                .first()
                .toUpperCase()
                .toString()
        }
    }
}