package pl.valueadd.restcountries.presentation.main.first

import android.util.Log
import pl.valueadd.restcountries.domain.manager.ExampleDomainManager
import pl.valueadd.restcountries.domain.manager.ExceptionDomainManager
import pl.valueadd.restcountries.utility.reactivex.observeOnMain
import pl.valueadd.restcountries.utility.reactivex.subscribeOnIo
import io.reactivex.rxkotlin.addTo
import org.apache.commons.lang3.math.NumberUtils
import javax.inject.Inject

class FirstPresenter @Inject constructor(
    private val exampleManager: ExampleDomainManager,
    private val exceptionManager: ExceptionDomainManager
) : pl.valueadd.restcountries.presentation.base.BasePresenter<FirstView>() {

    override fun attachView(view: FirstView) {
        super.attachView(view)

        observeCount()
    }

    fun onAboutButtonClick() = onceViewAttached {
        it.navigateToAboutView()
    }

    fun increaseSelectedCountOfExamples() =
        setSelectedCountOfExamples(++count)

    fun decreaseSelectedCountOfExamples() =
        setSelectedCountOfExamples(--count)

    private var count: Int = NumberUtils.INTEGER_ZERO

    private fun observeCount() {
        exampleManager
            .observeSelectedCountOfExamples(count)
            .observeOnMain()
            .subscribe(
                ::handleObserveCountSuccess,
                ::handleObserveCountFailed
            )
            .addTo(disposables)
    }

    private fun handleObserveCountSuccess(count: Int) = onceViewAttached {

        this.count = count

        it.bindCountToView(count)
    }

    private fun handleObserveCountFailed(throwable: Throwable) = onceViewAttached {

        Log.w(this::class.java.simpleName, throwable.message)

        val message = exceptionManager.mapToMessage(throwable)

        it.showError(message)
    }

    private fun setSelectedCountOfExamples(value: Int) {
        exampleManager
            .setSelectedCountOfExamples(value)
            .subscribeOnIo()
            .subscribe(
                ::handleSetSelectedCountOfExamplesSuccess,
                ::handleSetSelectedCountOfExamplesFailed
            )
            .addTo(disposables)
    }

    private fun handleSetSelectedCountOfExamplesSuccess() {

        Log.d(this::class.java.simpleName, "Count has been set.")
    }

    private fun handleSetSelectedCountOfExamplesFailed(throwable: Throwable) = onceViewAttached {

        Log.w(this::class.java.simpleName, throwable.message)

        val message = exceptionManager.mapToMessage(throwable)

        it.showError(message)
    }
}