package pl.valueadd.restcountries.utility.reactivex

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Contains all common subscription calls.
 * Do not extend with additional calls.
 */

/**
 * Shorter call:
 *
 * ```.subscribeOn(Schedulers.io())```
 */
fun <T> Flowable<T>.subscribeOnIo(): Flowable<T> =
    this.subscribeOn(Schedulers.io())

/**
 * Shorter call:
 *
 * ```.subscribeOn(Schedulers.io())```
 */
fun <T> Observable<T>.subscribeOnIo(): Observable<T> =
    this.subscribeOn(Schedulers.io())

/**
 * Shorter call:
 *
 * ```.subscribeOn(Schedulers.io())```
 */
fun <T> Single<T>.subscribeOnIo(): Single<T> =
    this.subscribeOn(Schedulers.io())

/**
 * Shorter call:
 *
 * ```.subscribeOn(Schedulers.io())```
 */
fun <T> Maybe<T>.subscribeOnIo(): Maybe<T> =
    this.subscribeOn(Schedulers.io())

/**
 * Shorter call:
 *
 * ```.subscribeOn(Schedulers.io())```
 */
fun Completable.subscribeOnIo(): Completable =
    this.subscribeOn(Schedulers.io())

/**
 * Shorter call:
 *
 * ```
 * .subscribeOn(Schedulers.io())
 * .observeOn(AndroidSchedulers.mainThread())
 * ```
 */
fun <T> Flowable<T>.observeOnMain(): Flowable<T> =
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

/**
 * Shorter call:
 *
 * ```
 * .subscribeOn(Schedulers.io())
 * .observeOn(AndroidSchedulers.mainThread())
 * ```
 */
fun <T> Observable<T>.observeOnMain(): Observable<T> =
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

/**
 * Shorter call:
 *
 * ```
 * .subscribeOn(Schedulers.io())
 * .observeOn(AndroidSchedulers.mainThread())
 * ```
 */
fun <T> Single<T>.observeOnMain(): Single<T> =
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

/**
 * Shorter call:
 *
 * ```
 * .subscribeOn(Schedulers.io())
 * .observeOn(AndroidSchedulers.mainThread())
 * ```
 */
fun <T> Maybe<T>.observeOnMain(): Maybe<T> =
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

/**
 * Shorter call:
 *
 * ```
 * .subscribeOn(Schedulers.io())
 * .observeOn(AndroidSchedulers.mainThread())
 * ```
 */
fun Completable.observeOnMain(): Completable =
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())