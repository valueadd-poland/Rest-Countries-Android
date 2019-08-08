package pl.valueadd.restcountries.presentation.main.countries.list.item

import android.view.View
import androidx.core.content.ContextCompat
import pl.valueadd.restcountries.R
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.ModelAbstractItem
import kotlinx.android.synthetic.main.country_item.view.flagImageView
import kotlinx.android.synthetic.main.country_item.view.titleTextView
import pl.valueadd.restcountries.domain.model.country.CountryModel
import pl.valueadd.restcountries.utility.image.clearImage
import pl.valueadd.restcountries.utility.image.loadSVGImage

class CountryItem(model: CountryModel) :
    ModelAbstractItem<CountryModel, CountryItem.CountryViewHolder>(model) {

    override val layoutRes: Int
        get() = R.layout.country_item

    override val type: Int
        get() = R.id.item_example

    override var identifier: Long
        get() = model.numericCode.hashCode().toLong()
        set(_) = Unit

    override fun getViewHolder(v: View): CountryViewHolder =
        CountryViewHolder(v)

    class CountryViewHolder(view: View) : FastAdapter.ViewHolder<CountryItem>(view) {

        override fun bindView(item: CountryItem, payloads: MutableList<Any>) = itemView.run {
            item.model.let {
                titleTextView.text = it.name
                flagImageView.loadSVGImage(it.flag, R.drawable.ic_language_white_24dp, ContextCompat.getColor(context, R.color.blackAlpha40))
            }
        }

        override fun unbindView(item: CountryItem) = itemView.run {
            flagImageView.clearImage()
        }
    }
}