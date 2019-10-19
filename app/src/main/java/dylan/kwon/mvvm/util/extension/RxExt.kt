package dylan.kwon.mvvm.util.extension

import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun timer(delay: Long, onDelayed: () -> Unit): Disposable = Completable.timer(delay, TimeUnit.MILLISECONDS)
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe(onDelayed, {
        it.printStackTrace()
    })
