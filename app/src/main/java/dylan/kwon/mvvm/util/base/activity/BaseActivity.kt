package dylan.kwon.mvvm.util.base.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dylan.kwon.mvvm.util.LogUtil
import dylan.kwon.mvvm.util.system.KeyboardUtil
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject

@SuppressLint("Registered")
abstract class BaseActivity : AppCompatActivity() {

    protected val tag: String = this.javaClass.simpleName

    protected lateinit var instanceState: Bundle

    protected val keyboardUtil: KeyboardUtil by inject()

    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.d(tag, "onCreate!!")

        instanceState = savedInstanceState ?: intent?.extras ?: Bundle()
        onSetupInstanceState(instanceState)
    }

    override fun onStart() {
        super.onStart()
        LogUtil.d(tag, "onStart!!")
    }

    override fun onResume() {
        super.onResume()
        LogUtil.d(tag, "onResume!!")
    }

    override fun onPause() {
        super.onPause()
        LogUtil.d(tag, "onPause!!")
        keyboardUtil.hideKeyboard(this)
    }

    override fun onStop() {
        super.onStop()
        LogUtil.d(tag, "onStop!!")
    }

    override fun onRestart() {
        super.onRestart()
        LogUtil.d(tag, "onRestart!!")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.d(tag, "onDestroy!!")
        compositeDisposable.clear()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        LogUtil.d(tag, "onNewIntent!!")
        setIntent(intent)
    }

    override fun setIntent(newIntent: Intent?) {
        super.setIntent(newIntent)
        instanceState = newIntent?.extras ?: Bundle()
        onSetupInstanceState(instanceState)
    }

    open fun onSetupInstanceState(savedInstanceState: Bundle) {
        LogUtil.d(tag, "onSetupInstanceState!!")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        LogUtil.d(tag, "onSaveInstanceState!!")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        LogUtil.d(tag, "onActivityResult!!")
    }

}
