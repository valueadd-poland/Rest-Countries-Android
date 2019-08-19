package pl.valueadd.restcountries.view.decorator

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.country_item.view.titleTextView
import pl.valueadd.restcountries.R

class CountryItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val paint: Paint = Paint()
    private val heightDp: Int
    private var marginStart = 0

    init {
        paint.style = Paint.Style.FILL
        paint.color = ContextCompat.getColor(context, R.color.propertyDivider)

        val pixelsDimension = context.resources.getDimension(R.dimen._1dp)
        val dimension = pixelsDimension / (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)

        heightDp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimension, context.resources.displayMetrics).toInt()
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        for (i in 0 until parent.childCount) {

            val view = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(view)

            if (position != RecyclerView.NO_POSITION) {

                val viewType = parent.adapter?.getItemViewType(position)

                if (viewType == R.id.item_country) {

                    marginStart = parent
                        .getChildViewHolder(view)
                        .itemView
                        .titleTextView
                        .left

                    c.drawRect(view.left.toFloat() + marginStart, view.bottom.toFloat(), view.right.toFloat(), view.bottom + heightDp.toFloat(), paint)
                }
            }
        }
    }
}