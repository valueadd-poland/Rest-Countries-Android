package pl.valueadd.restcountries.presentation.main.second

import android.util.Log
import pl.valueadd.restcountries.domain.manager.ExampleDomainManager
import pl.valueadd.restcountries.domain.manager.ExceptionDomainManager
import pl.valueadd.restcountries.domain.model.ExampleModel
import pl.valueadd.restcountries.presentation.main.second.item.ExampleItem
import pl.valueadd.restcountries.utility.reactivex.observeOnMain
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class SecondPresenter
@Inject constructor(
    private val exampleManager: ExampleDomainManager,
    private val exceptionManager: ExceptionDomainManager
) : pl.valueadd.restcountries.presentation.base.BasePresenter<SecondView>() {

    override fun attachView(view: SecondView) {
        super.attachView(view)

        downloadAllExamples()

        observeAllExamples()
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

        val items = list.map { ExampleItem(it) }

        view.bindDataToList(items)
    }

    private fun handleObserveAllExamplesFailed(throwable: Throwable) = onceViewAttached {

        val message = exceptionManager.mapToMessage(throwable)

        it.showError(message)
    }
}