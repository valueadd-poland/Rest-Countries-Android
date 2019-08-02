package pl.valueadd.restcountries.presentation.main.countries.list

import android.util.Log
import io.reactivex.rxkotlin.addTo
import pl.valueadd.restcountries.domain.manager.ExampleDomainManager
import pl.valueadd.restcountries.domain.manager.ExceptionDomainManager
import pl.valueadd.restcountries.domain.model.ExampleModel
import pl.valueadd.restcountries.presentation.base.BasePresenter
import pl.valueadd.restcountries.presentation.main.countries.list.item.ClickCountryItemEventHook
import pl.valueadd.restcountries.presentation.main.countries.list.item.CountryItem
import pl.valueadd.restcountries.utility.reactivex.observeOnMain
import javax.inject.Inject

class CountryListPresenter
@Inject constructor(
    private val exampleManager: ExampleDomainManager,
    private val exceptionManager: ExceptionDomainManager
) : BasePresenter<CountryListView>(),
    ClickCountryItemEventHook.Listener {

    override fun attachView(view: CountryListView) {
        super.attachView(view)

        downloadAllExamples()

        observeAllExamples()
    }

    override fun onCountryItemClick() = onceViewAttached {
        it.navigateToCountryDetailsView()
    }

    private fun downloadAllExamples() {

        exampleManager
            .downloadMockedExamples()
            .observeOnMain()
            .subscribe(
                ::handleDownloadAllExamplesSuccess,
                ::handleDownloadAllExamplesFailed
            )
            .addTo(disposables)
    }

    private fun handleDownloadAllExamplesSuccess() {

        Log.d(this::class.java.simpleName, "Examples has been downloaded successfully.")
    }

    private fun handleDownloadAllExamplesFailed(throwable: Throwable) = onceViewAttached {

        val message = exceptionManager.mapToMessage(throwable)

        it.showError(message)
    }

    private fun observeAllExamples() {
        exampleManager
            .observeAllExamples()
            .observeOnMain()
            .subscribe(
                ::handleObserveAllExamplesSuccess,
                ::handleObserveAllExamplesFailed
            )
            .addTo(disposables)
    }

    private fun handleObserveAllExamplesSuccess(list: List<ExampleModel>) = onceViewAttached { view ->

        val items = list.map { CountryItem(it) }

        view.bindDataToList(items)
    }

    private fun handleObserveAllExamplesFailed(throwable: Throwable) = onceViewAttached {

        val message = exceptionManager.mapToMessage(throwable)

        it.showError(message)
    }
}