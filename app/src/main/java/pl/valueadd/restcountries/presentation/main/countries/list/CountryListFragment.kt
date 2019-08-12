package pl.valueadd.restcountries.presentation.main.countries.list

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.adapters.GenericFastItemAdapter
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration
import kotlinx.android.synthetic.main.country_fragment_list.recyclerView
import pl.valueadd.restcountries.R
import pl.valueadd.restcountries.presentation.base.fragment.base.BaseMVPFragment
import pl.valueadd.restcountries.presentation.main.countries.details.CountryDetailsFragment
import pl.valueadd.restcountries.presentation.main.countries.list.item.ClickCountryItemEventHook
import pl.valueadd.restcountries.presentation.main.countries.list.item.CountryHeaderAdapter
import pl.valueadd.restcountries.presentation.main.root.RootFragment
import pl.valueadd.restcountries.view.decorator.CountryItemDecoration
import javax.inject.Inject

class CountryListFragment : BaseMVPFragment<CountryListView, CountryListPresenter>(R.layout.country_fragment_list),
    CountryListView {

    companion object {
        fun createInstance(): CountryListFragment =
            CountryListFragment()
    }

    @Inject
    override lateinit var mPresenter: CountryListPresenter

    private val stickyHeaderAdapter = CountryHeaderAdapter()

    private val listAdapter: GenericFastItemAdapter
        by lazy { GenericFastItemAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    override fun bindDataToList(list: List<IItem<*>>) {
        listAdapter.setNewList(list)
    }

    override fun navigateToCountryDetailsView(countryId: String) {
        val fragment = CountryDetailsFragment.createInstance(countryId)

        getParentFragment(RootFragment::class.java)
            .start(fragment)
    }

    private fun setupView() {

        val headersDecoration = StickyRecyclerHeadersDecoration(stickyHeaderAdapter)

        recyclerView.apply {
            adapter = stickyHeaderAdapter.wrap(listAdapter)
            addItemDecoration(CountryItemDecoration(requireContext()))
            addItemDecoration(headersDecoration)
        }

        stickyHeaderAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                headersDecoration.invalidateHeaders()
            }
        })

        listAdapter.apply {
            addEventHook(ClickCountryItemEventHook(presenter))
        }
    }
}