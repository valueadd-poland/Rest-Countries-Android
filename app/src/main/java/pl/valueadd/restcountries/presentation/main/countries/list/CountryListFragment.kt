package pl.valueadd.restcountries.presentation.main.countries.list

import android.os.Bundle
import android.view.View
import pl.valueadd.restcountries.R
import pl.valueadd.restcountries.presentation.base.fragment.base.BaseMVPFragment
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import kotlinx.android.synthetic.main.country_fragment_list.recyclerView
import pl.valueadd.restcountries.presentation.main.countries.details.CountryDetailsFragment
import pl.valueadd.restcountries.presentation.main.countries.list.item.ClickCountryItemEventHook
import pl.valueadd.restcountries.presentation.main.root.RootFragment
import javax.inject.Inject

class CountryListFragment : BaseMVPFragment<CountryListView, CountryListPresenter>(R.layout.country_fragment_list),
    CountryListView {

    companion object {
        fun createInstance(): CountryListFragment =
            CountryListFragment()
    }

    @Inject
    override lateinit var mPresenter: CountryListPresenter

    private val listAdapter: FastItemAdapter<IItem<*>>
        by lazy { FastItemAdapter<IItem<*>>() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    override fun bindDataToList(list: List<IItem<*>>) {
        listAdapter.setNewList(list)
    }

    override fun navigateToCountryDetailsView() {
        val fragment = CountryDetailsFragment.createInstance()

        getParentFragment(RootFragment::class.java)
            .start(fragment)
    }

    private fun setupView() {

        recyclerView.adapter = listAdapter.apply {
            addEventHook(ClickCountryItemEventHook(presenter))
        }
    }
}