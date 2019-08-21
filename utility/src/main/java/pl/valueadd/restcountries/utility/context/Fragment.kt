package pl.valueadd.restcountries.utility.context

import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog


fun Fragment.createBottomSheetDialog(@LayoutRes resId: Int): BottomSheetDialog {
    return BottomSheetDialog(requireContext()).apply {
        val sheetView: View = requireActivity().layoutInflater.inflate(resId, null)

        setContentView(sheetView)
        show()
    }
}