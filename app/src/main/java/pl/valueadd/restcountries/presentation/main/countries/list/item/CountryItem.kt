package pl.valueadd.restcountries.presentation.main.countries.list.item

import android.view.View
import pl.valueadd.restcountries.R
import pl.valueadd.restcountries.domain.model.ExampleModel
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.ModelAbstractItem
import kotlinx.android.synthetic.main.item_example.view.itemTitle

class CountryItem(model: ExampleModel) :
    ModelAbstractItem<ExampleModel, CountryItem.CountryViewHolder>(model) {

    override val layoutRes: Int
        get() = R.layout.item_example

    override val type: Int
        get() = R.id.item_example

    override var identifier: Long
        get() = model.id
        set(_) = Unit

    override fun getViewHolder(v: View): CountryViewHolder =
        CountryViewHolder(v)

    class CountryViewHolder(view: View) : FastAdapter.ViewHolder<CountryItem>(view) {

        override fun bindView(item: CountryItem, payloads: MutableList<Any>) = with(itemView) {
            itemTitle.text = item.model.title
        }

        override fun unbindView(item: CountryItem) {
            // no-op
            // here could you free up resources from Glide
        }
    }
}