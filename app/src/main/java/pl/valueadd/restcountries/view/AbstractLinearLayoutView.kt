package pl.valueadd.restcountries.view

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.SparseArray
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import java.io.Serializable

abstract class AbstractLinearLayoutView : LinearLayoutCompat {

    constructor(context: Context, @LayoutRes layoutResource: Int) : super(context) {
        this.layoutResource = layoutResource
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?, @LayoutRes layoutResource: Int) : super(context, attrs) {
        this.layoutResource = layoutResource
        this.attrs = attrs
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, @LayoutRes layoutResource: Int) : super(context, attrs, defStyleAttr) {
        this.layoutResource = layoutResource
        this.attrs = attrs
        initView()
    }

    protected var attrs: AttributeSet? = null

    @LayoutRes
    protected val layoutResource: Int

    @OrientationMode
    protected open val layoutOrientation: Int
        get() = VERTICAL

    @get:DrawableRes
    protected open val backgroundResource: Int
        get() = NO_ID

    @get:ColorRes
    protected open val backgroundColorResource: Int
        get() = NO_ID

    protected open val layoutWidth: Int
        get() = ViewGroup.LayoutParams.MATCH_PARENT

    protected open val layoutHeight: Int
        get() = ViewGroup.LayoutParams.MATCH_PARENT

    protected open fun applyDefaultParams() {
        orientation = layoutOrientation
        layoutParams = LayoutParams(layoutWidth, layoutHeight)
        setBackground()
    }

    protected open fun collectAttrs() {
        // You could override it to gather the attributes.
    }

    private fun initView() {
        inflate(layoutResource)
        applyDefaultParams()
        collectAttrs()
    }

    private fun setBackground() {
        if (backgroundColorResource == NO_ID && backgroundResource == NO_ID) {
            return
        }

        if (backgroundColorResource != NO_ID && backgroundResource != NO_ID) {
            throw IllegalArgumentException("you can choose only one background resource")
        }

        if (backgroundResource != NO_ID) {
            setBackgroundResource(backgroundResource)
        } else if (backgroundColorResource != NO_ID) {
            setBackgroundColor(ContextCompat.getColor(context, backgroundColorResource))
        }
    }

    private fun inflate(layoutId: Int) =
        inflate(context, layoutId, this)

    override fun onSaveInstanceState(): Parcelable? {
        val superState: Parcelable? = super.onSaveInstanceState()
        val ss = SavedState(superState)
        ss.childrenStates = SparseArray()
        for (i in 0 until childCount) {
            getChildAt(i).saveHierarchyState(ss.childrenStates)
        }
        return ss
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state !is SavedState) {
            super.onRestoreInstanceState(state)
            return
        }
        val ss: SavedState = state
        super.onRestoreInstanceState(ss.superState)
        for (i in 0 until childCount) {
            getChildAt(i).restoreHierarchyState(ss.childrenStates)
        }
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>?) {
        dispatchFreezeSelfOnly(container)
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>?) {
        dispatchThawSelfOnly(container)
    }

    protected open class SavedState : BaseSavedState {
        internal var childrenStates: SparseArray<Parcelable>? = null
        internal var serializable: Serializable? = null

        constructor(superState: Parcelable?) : super(superState)

        protected constructor(parcel: Parcel, classLoader: ClassLoader?) : super(parcel) {
            childrenStates = parcel.readSparseArray(classLoader) as SparseArray<Parcelable>
            serializable = parcel.readSerializable()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeSparseArray(childrenStates as SparseArray<Any>?)
            out.writeSerializable(serializable)
        }

        companion object {

            @JvmField
            val CREATOR: Parcelable.ClassLoaderCreator<SavedState> = object : Parcelable.ClassLoaderCreator<SavedState> {
                override fun createFromParcel(source: Parcel, loader: ClassLoader?): SavedState {
                    return SavedState(source, loader)
                }

                override fun createFromParcel(source: Parcel): SavedState {
                    return createFromParcel(source, null)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}