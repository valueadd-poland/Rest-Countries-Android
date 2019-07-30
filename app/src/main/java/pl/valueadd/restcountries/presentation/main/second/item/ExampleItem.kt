package pl.valueadd.restcountries.presentation.main.second.item

import android.view.View
import pl.valueadd.restcountries.R
import pl.valueadd.restcountries.domain.model.ExampleModel
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.ModelAbstractItem
import kotlinx.android.synthetic.main.item_example.view.itemTitle

class ExampleItem(model: ExampleModel) :
    ModelAbstractItem<ExampleModel, ExampleItem.ExampleViewHolder>(model) {

    override val layoutRes: Int
        get() = R.layout.item_example

    override val type: Int
        get() = R.id.item_example

    override var identifier: Long
        get() = model.id
        set(_) = Unit

    override fun getViewHolder(v: View): ExampleViewHolder =
        ExampleViewHolder(v)

    class ExampleViewHolder(view: View) : FastAdapter.ViewHolder<ExampleItem>(view) {

        override fun bindView(item: ExampleItem, payloads: MutableList<Any>) = with(itemView) {
            itemTitle.text = item.model.title
        }

        override fun unbindView(item: ExampleItem) {
            // no-op
            // here could you free up resources from Glide
        }
    }
}