package dylan.kwon.mvvm.util.base.adapter

import android.content.Context
import io.reactivex.disposables.CompositeDisposable

interface ViewHolder {

    val applicationContext: Context

    val compositeDisposable: CompositeDisposable

}
