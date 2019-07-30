package pl.valueadd.restcountries.presentation.main.second

import android.os.Bundle
import android.view.View
import pl.valueadd.restcountries.R
import pl.valueadd.restcountries.presentation.base.fragment.base.BaseMVPFragment
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import kotlinx.android.synthetic.main.fragment_second.recyclerView
import javax.inject.Inject

class SecondFragment : BaseMVPFragment<SecondView, SecondPresenter>(R.layout.fragment_second),
    SecondView {

    companion object {
        fun createInstance(): SecondFragment =
            SecondFragment()
    }

    @Inject
    override lateinit var mPresenter: SecondPresenter

    private val listAdapter: FastItemAdapter<IItem<*>>
        by lazy { FastItemAdapter<IItem<*>>() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    override fun bindDataToList(list: List<IItem<*>>) {
        listAdapter.setNewList(list)
    }

    private fun setupView() {

        recyclerView.adapter = listAdapter
    }
}