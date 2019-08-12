package pl.valueadd.restcountries.presentation.main.countries.list.item

import android.view.View
import androidx.core.content.ContextCompat
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.ModelAbstractItem
import kotlinx.android.synthetic.main.country_item.view.flagImageView
import kotlinx.android.synthetic.main.country_item.view.subTitleTextView
import kotlinx.android.synthetic.main.country_item.view.titleTextView
import pl.valueadd.restcountries.R
import pl.valueadd.restcountries.domain.model.country.CountryModel
import pl.valueadd.restcountries.utility.image.clearImage
import pl.valueadd.restcountries.utility.image.loadSVGImage

class CountryItem(model: CountryModel) :
    ModelAbstractItem<CountryModel, CountryItem.ViewHolder>(model) {

    override val layoutRes: Int
        get() = R.layout.country_item

    override val type: Int
        get() = R.id.item_country

    override var identifier: Long
        get() = model.numericCode.hashCode().toLong()
        set(_) = Unit

    override fun getViewHolder(v: View): ViewHolder =
        ViewHolder(v)

    class ViewHolder(view: View) : FastAdapter.ViewHolder<CountryItem>(view) {

        override fun bindView(item: CountryItem, payloads: MutableList<Any>) = itemView.run {
            item.model.let {
                titleTextView.text = it.name
                subTitleTextView.text = it.translations.toString()
                flagImageView.loadSVGImage(it.flagUrl, R.drawable.ic_language_white_24dp, ContextCompat.getColor(context, R.color.blackAlpha40))
            }
        }

        override fun unbindView(item: CountryItem) = itemView.run {
            flagImageView.clearImage()
        }
    }
}