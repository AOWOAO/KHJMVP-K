package com.xhkj.khjmvp.utils

import java.util.concurrent.TimeUnit

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable

object RxTimerUtil {
    private var mDisposable: Disposable? = null

    /** milliseconds毫秒后执行next操作
     *
     * @param milliseconds
     * @param next
     */
    fun timer(milliseconds: Long, next: IRxNext?) {
        Observable.timer(milliseconds, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long> {
                override fun onSubscribe(@NonNull disposable: Disposable) {
                    mDisposable = disposable
                }

                override fun onNext(t: Long) {
                    next?.doNext(t)
                }

                override fun onError(@NonNull e: Throwable) {
                    //取消订阅
                    cancel()
                }

                override fun onComplete() {
                    //取消订阅
                    cancel()
                }
            })
    }


    /** 每隔milliseconds毫秒后执行next操作
     *
     * @param milliseconds
     * @param next
     */
    fun interval(milliseconds: Long, next: IRxNext?) {
        Observable.interval(milliseconds, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long> {
                override fun onSubscribe(@NonNull disposable: Disposable) {
                    mDisposable = disposable
                }

                override fun onNext(t: Long) {
                    next?.doNext(t)
                }

                override fun onError(@NonNull e: Throwable) {

                }

                override fun onComplete() {

                }
            })
    }


    /**
     * 取消订阅
     */
    fun cancel() {
        if (mDisposable != null && !mDisposable!!.isDisposed) {
            mDisposable!!.dispose()
        }
    }

    interface IRxNext {
        fun doNext(number: Long)
    }
}
