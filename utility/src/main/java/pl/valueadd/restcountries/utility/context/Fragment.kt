package pl.valueadd.restcountries.utility.context

import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

inline fun Fragment.createBottomSheetDialog(
    @LayoutRes resId: Int,
    crossinline onCreate: (dialog: BottomSheetDialog, view: View) -> Unit = { _, _ -> /* EMPTY */ }
): BottomSheetDialog {
    return BottomSheetDialog(requireContext()).apply {
        val sheetView: View = requireActivity().layoutInflater.inflate(resId, null)

        setContentView(sheetView)

        // Expand the whole dialog in landscape mode.
        (sheetView.parent as? FrameLayout)?.let {
            BottomSheetBehavior.from(it).state = BottomSheetBehavior.STATE_EXPANDED
        }

        onCreate(this, sheetView)
    }
}