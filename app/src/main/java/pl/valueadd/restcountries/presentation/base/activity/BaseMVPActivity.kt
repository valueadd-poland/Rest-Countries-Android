package pl.valueadd.restcountries.presentation.base.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import me.yokeyword.fragmentation.ExtraTransaction
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportActivityDelegate
import me.yokeyword.fragmentation.SupportHelper
import me.yokeyword.fragmentation.anim.FragmentAnimator
import pl.valueadd.restcountries.R
import pl.valueadd.restcountries.presentation.base.BasePresenter
import pl.valueadd.restcountries.presentation.base.BaseView
import pl.valueadd.restcountries.presentation.base.fragment.base.IBaseFragment
import pl.valueadd.restcountries.utility.dependencyinjection.DependencyUtil
import pl.valueadd.restcountries.utility.view.snackbar.SnackbarUtil
import javax.inject.Inject

abstract class BaseMVPActivity<V : BaseView, P : BasePresenter<V>>(
    @LayoutRes protected val layoutResourceId: Int = R.layout.activity_base_layout
) :
    MvpActivity<V, P>(),
    IBaseActivity,
    BaseView {

    private val delegate: SupportActivityDelegate
        by lazy { SupportActivityDelegate(this) }

    @Inject
    lateinit var snackBarUtil: SnackbarUtil

    @get:IdRes
    protected open val fragmentContainer: Int
        get() = R.id.fragmentContainer

    protected open val menuLayout: Int
        get() = R.menu.main_menu

    /**
     * Contains disposable subscriptions of streams.
     */
    protected val disposables: CompositeDisposable
        by lazy { CompositeDisposable() }

    /**
     * Add subscription to composite.
     */
    protected fun addDisposable(disposable: Disposable): Boolean =
        disposables.add(disposable)

    /**
     * Clear all subscriptions.
     */
    protected fun clearDisposables() {
        disposables.clear()
    }

    protected fun showError(error: String, view: View) =
        snackBarUtil.showMessage(view, error, ContextCompat.getColor(this, R.color.red))

    /* MvpActivity */

    abstract val mPresenter: P

    override fun createPresenter(): P =
        mPresenter

    /* BaseView */

    override fun showError(message: String) {
        showError(message, findViewById(android.R.id.content))
    }

    /* Life cycle methods */

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        DependencyUtil.openScopeAndInject(this, pl.valueadd.restcountries.application.ActivityModule(this))

        super.onCreate(savedInstanceState)
        delegate.onCreate(savedInstanceState)

        setContentView(layoutResourceId)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        delegate.onPostCreate(savedInstanceState)
    }

    override fun onDestroy() {
        clearDisposables()

        delegate.onDestroy()
        super.onDestroy()

        DependencyUtil.closeScope(this)
    }

    override fun onBackPressed() {
        // Do not invoke super method.
        delegate.onBackPressed()
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(menuItem)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(menuLayout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean =
        delegate.dispatchTouchEvent(ev) || super.dispatchTouchEvent(ev)

    /* ISupportActivity */

    override fun setFragmentAnimator(fragmentAnimator: FragmentAnimator?) {
        delegate.fragmentAnimator = fragmentAnimator
    }

    override fun getFragmentAnimator(): FragmentAnimator =
        delegate.fragmentAnimator

    override fun onBackPressedSupport(): Unit =
        delegate.onBackPressedSupport()

    override fun extraTransaction(): ExtraTransaction =
        delegate.extraTransaction()

    override fun onCreateFragmentAnimator(): FragmentAnimator =
        delegate.onCreateFragmentAnimator()

    override fun getSupportDelegate(): SupportActivityDelegate =
        delegate

    override fun post(runnable: Runnable?): Unit =
        delegate.post(runnable)

    /* IBaseActivity */

    override fun loadRootFragment(containerId: Int, toFragment: IBaseFragment) {
        delegate.loadRootFragment(containerId, toFragment)
    }

    override fun start(toFragment: IBaseFragment) {
        delegate.start(toFragment)
    }

    override fun start(toFragment: IBaseFragment, @ISupportFragment.LaunchMode launchMode: Int) {
        delegate.start(toFragment, launchMode)
    }

    override fun startWithPopTo(
        toFragment: IBaseFragment,
        targetFragmentClass: Class<*>,
        includeTargetFragment: Boolean
    ) {
        delegate.startWithPopTo(toFragment, targetFragmentClass, includeTargetFragment)
    }

    override fun pop() {
        delegate.pop()
    }

    override fun popTo(targetFragmentClass: Class<*>, includeTargetFragment: Boolean) {
        delegate.popTo(targetFragmentClass, includeTargetFragment)
    }

    override fun popTo(
        targetFragmentClass: Class<*>,
        includeTargetFragment: Boolean,
        afterPopTransactionRunnable: Runnable
    ) {
        delegate.popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable)
    }

    override fun popTo(
        targetFragmentClass: Class<*>,
        includeTargetFragment: Boolean,
        afterPopTransactionRunnable: Runnable,
        popAnim: Int
    ) {
        delegate.popTo(
            targetFragmentClass,
            includeTargetFragment,
            afterPopTransactionRunnable,
            popAnim
        )
    }

    override fun <F : IBaseFragment> findFragment(fragmentClass: Class<F>): F? =
        SupportHelper.findFragment(supportFragmentManager, fragmentClass)

    override fun getTopFragment(): ISupportFragment {
        return SupportHelper.getTopFragment(supportFragmentManager)
    }

    /**
     * Returns top fragment as desired class.
     *
     * If fragment is not a given class this returns null.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ISupportFragment> getTopFragment(fragmentClass: Class<T>): T? {
        val topFragment = SupportHelper.getTopFragment(supportFragmentManager)
        return if (topFragment != null && topFragment.javaClass == fragmentClass) {
            topFragment as T
        } else null
    }
}
