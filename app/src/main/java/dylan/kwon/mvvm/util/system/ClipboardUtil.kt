package dylan.kwon.mvvm.util.system

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import dylan.kwon.mvvm.Constant
import dylan.kwon.mvvm.R

class ClipboardUtil(val applicationContext: Context) {

    private val instance: ClipboardManager =
        applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager


    /**
     * 텍스트를 클립보드에 저장하기 전 팝업을 띄움
     *
     * @param context context
     * @param text    클립보드에 저장할 텍스트
     */
    fun showPrimaryClipPopup(context: Context, text: String?) {
        val items: Array<CharSequence> = arrayOf(
            context.resources.getString(R.string.copy)
        )
        AlertDialog.Builder(context)
            .setItems(items) { _, _ ->
                setPrimaryClip(text)
            }
            .create()
            .show()
    }

    /**
     * 텍스트를 클립보드에 저장
     *
     * @param text    클립보드에 저장할 텍스트
     */
    fun setPrimaryClip(text: String?) {
        val clipData: ClipData = ClipData.newPlainText(Constant.TAG, text ?: "")
        instance.setPrimaryClip(clipData)
    }

    /**
     *  저장된 텍스트를 리턴
     */
    fun getPrimaryClip(index: Int): String? = try {
        instance.primaryClip?.getItemAt(index)?.text?.toString()

    } catch (e: NullPointerException) {
        null
    }

}
