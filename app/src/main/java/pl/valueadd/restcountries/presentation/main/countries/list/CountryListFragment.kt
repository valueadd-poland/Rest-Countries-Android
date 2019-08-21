package pl.valueadd.restcountries.presentation.main.countries.list

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.adapters.GenericFastItemAdapter
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration
import kotlinx.android.synthetic.main.country_dialog_sort_name.segmentedRadioGroup
import kotlinx.android.synthetic.main.country_fragment_details.bordersChipGroup
import kotlinx.android.synthetic.main.country_fragment_list.recyclerView
import kotlinx.android.synthetic.main.country_fragment_list.sortByNameChip
import kotlinx.android.synthetic.main.toolbar.searchView
import org.apache.commons.lang3.StringUtils.EMPTY
import pl.valueadd.restcountries.R
import pl.valueadd.restcountries.presentation.base.fragment.viewstate.base.BaseMVPViewStateFragment
import pl.valueadd.restcountries.presentation.main.countries.details.CountryDetailsFragment
import pl.valueadd.restcountries.presentation.main.countries.list.item.ClickCountryItemEventHook
import pl.valueadd.restcountries.presentation.main.countries.list.item.CountryHeaderAdapter
import pl.valueadd.restcountries.presentation.main.root.RootFragment
import pl.valueadd.restcountries.utility.context.createBottomSheetDialog
import pl.valueadd.restcountries.utility.reactivex.onSuccess
import pl.valueadd.restcountries.utility.reactivex.throttleClicks
import pl.valueadd.restcountries.view.decorator.CountryItemDecoration
import javax.inject.Inject

class CountryListFragment : BaseMVPViewStateFragment<CountryListView, CountryListPresenter, CountryListViewState>(R.layout.country_fragment_list),
    CountryListView,
    SearchView.OnQueryTextListener {

    companion object {
        fun createInstance(): CountryListFragment =
            CountryListFragment()
    }

    @Inject
    override lateinit var mPresenter: CountryListPresenter

    override val searchQuery: String
        get() = searchView?.query?.toString() ?: EMPTY

    private val stickyHeaderAdapter = CountryHeaderAdapter()

    private val listAdapter: GenericFastItemAdapter
        by lazy { GenericFastItemAdapter() }

    private var isSortByNameDialogDisplaying: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()

        initializeChipGroup()
    }

    override fun onSaveViewState() = viewState.let {
        it.isSortByNameDialogDisplaying = isSortByNameDialogDisplaying
    }

    override fun createViewState(): CountryListViewState =
        CountryListViewState()

    override fun bindDataToList(list: List<IItem<*>>) {
        listAdapter.setNewList(list)
    }

    override fun navigateToCountryDetailsView(countryId: String) {
        val fragment = CountryDetailsFragment.createInstance(countryId)

        getParentFragment(RootFragment::class.java)
            .start(fragment)
    }

    override fun onQueryTextSubmit(query: String?): Boolean =
        onQueryTextChange(query)

    override fun onQueryTextChange(newText: String?): Boolean {
        presenter.onQueryChanged(newText ?: EMPTY)

        return false
    }

    override fun showSortByNameDialog() {
        createBottomSheetDialog(R.layout.country_dialog_sort_name).apply {
            val id: Int = if (presenter.isAscending) {
                R.id.segmentedAscending
            } else {
                R.id.segmentedDescending
            }

            segmentedRadioGroup.check(id)

            segmentedRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                this.dismiss()
                presenter.onSortByNameChanged(R.id.segmentedAscending == checkedId)
            }

            setOnDismissListener {
                isSortByNameDialogDisplaying = false
            }

        }.show()

        isSortByNameDialogDisplaying = true
    }

    private fun initializeRecyclerView() {

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

    private fun initializeChipGroup() = bordersChipGroup.run {

        sortByNameChip
            .throttleClicks()
            .onSuccess(disposables, {
                presenter.onSortByNameViewClick()
            })
    }
}