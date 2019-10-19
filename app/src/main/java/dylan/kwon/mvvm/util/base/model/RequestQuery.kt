package dylan.kwon.mvvm.util.base.model

open class RequestQuery : HashMap<String, String>() {

    fun put(key: String, value: Any?) {
        value?.let {
            super.put(key, it.toString())
        }
    }

}
