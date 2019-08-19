package pl.valueadd.restcountries.view.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.GenericItem
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter
import org.apache.commons.lang3.math.NumberUtils.LONG_ZERO

/**
 * Created by mikepenz on 30.12.15.
 * This is a FastAdapter adapter implementation for the awesome Sticky-Headers lib by timehop
 * https://github.com/timehop/sticky-headers-recyclerview
 *
 * Modified for project usage.
 */
abstract class StickyHeaderAdapter<Item : GenericItem>
(@LayoutRes private val layoutRes: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    lateinit var fastAdapter: FastItemAdapter<Item>
        private set

    abstract fun onBindView(item: Item, itemView: View)

    /**
     * See [getHeaderId(Int)][com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter.getHeaderId]
     */
    abstract fun getHeaderId(item: Item, itemType: Int): Long

    override fun onCreateHeaderViewHolder(parent: ViewGroup?): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(layoutRes, parent, false)
        return object : RecyclerView.ViewHolder(view) {
            // no-op
        }
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            onBindView(it, holder.itemView)
        }
    }

    override fun getHeaderId(position: Int): Long =
        getItem(position)?.run {
            getHeaderId(this, getItemViewType(position))
        } ?: LONG_ZERO

    /**
     * Wrap the FastAdapter with this AbstractAdapter and keep its reference to forward all events correctly
     *
     * @param fastAdapter the FastAdapter which contains the base logic
     * @return this
     */
    fun wrap(fastAdapter: FastItemAdapter<Item>): StickyHeaderAdapter<Item> {
        this.fastAdapter = fastAdapter
        return this
    }

    /**
     * overwrite the registerAdapterDataObserver to correctly forward all events to the FastAdapter
     *
     * @param observer
     */
    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.registerAdapterDataObserver(observer)
        fastAdapter.registerAdapterDataObserver(observer)
    }

    /**
     * overwrite the unregisterAdapterDataObserver to correctly forward all events to the FastAdapter
     *
     * @param observer
     */
    override fun unregisterAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.unregisterAdapterDataObserver(observer)
        fastAdapter.unregisterAdapterDataObserver(observer)
    }

    /**
     * overwrite the getItemViewType to correctly return the value from the FastAdapter
     *
     * @param position
     * @return
     */
    override fun getItemViewType(position: Int): Int {
        return fastAdapter.getItemViewType(position)
    }

    /**
     * overwrite the getItemId to correctly return the value from the FastAdapter
     *
     * @param position
     * @return
     */
    override fun getItemId(position: Int): Long {
        return fastAdapter.getItemId(position)
    }

    /**
     * make sure we return the Item from the FastAdapter so we retrieve the item from all adapters
     *
     * @param position
     * @return
     */
    fun getItem(position: Int): Item? {
        return fastAdapter.getItem(position)
    }

    /**
     * make sure we return the count from the FastAdapter so we retrieve the count from all adapters
     *
     * @return
     */
    override fun getItemCount(): Int {
        return fastAdapter.itemCount
    }

    /**
     * the onCreateViewHolder is managed by the FastAdapter so forward this correctly
     *
     * @param parent
     * @param viewType
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val fastAdapter = this.fastAdapter
        return fastAdapter.onCreateViewHolder(parent, viewType)
    }

    /**
     * the onBindViewHolder is managed by the FastAdapter so forward this correctly
     *
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        fastAdapter.onBindViewHolder(holder, position)
    }

    /**
     * the onBindViewHolder is managed by the FastAdapter so forward this correctly
     *
     * @param holder
     * @param position
     * @param payloads
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        fastAdapter.onBindViewHolder(holder, position, payloads)
    }

    /**
     * the setHasStableIds is managed by the FastAdapter so forward this correctly
     *
     * @param hasStableIds
     */
    override fun setHasStableIds(hasStableIds: Boolean) {
        fastAdapter.setHasStableIds(hasStableIds)
    }

    /**
     * the onViewRecycled is managed by the FastAdapter so forward this correctly
     *
     * @param holder
     */
    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        fastAdapter.onViewRecycled(holder)
    }

    /**
     * the onFailedToRecycleView is managed by the FastAdapter so forward this correctly
     *
     * @param holder
     * @return
     */
    override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean {
        return fastAdapter.onFailedToRecycleView(holder)
    }

    /**
     * the onViewDetachedFromWindow is managed by the FastAdapter so forward this correctly
     *
     * @param holder
     */
    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        fastAdapter.onViewDetachedFromWindow(holder)
    }

    /**
     * the onViewAttachedToWindow is managed by the FastAdapter so forward this correctly
     *
     * @param holder
     */
    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        fastAdapter.onViewAttachedToWindow(holder)
    }

    /**
     * the onAttachedToRecyclerView is managed by the FastAdapter so forward this correctly
     *
     * @param recyclerView
     */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        fastAdapter.onAttachedToRecyclerView(recyclerView)
    }

    /**
     * the onDetachedFromRecyclerView is managed by the FastAdapter so forward this correctly
     *
     * @param recyclerView
     */
    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        fastAdapter.onDetachedFromRecyclerView(recyclerView)
    }
}