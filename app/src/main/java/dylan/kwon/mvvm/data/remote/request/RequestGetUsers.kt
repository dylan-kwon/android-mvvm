package dylan.kwon.mvvm.data.remote.request

import dylan.kwon.mvvm.util.base.model.Request
import dylan.kwon.mvvm.util.base.model.RequestQuery

data class RequestGetUsers(

    override val page: Int

) : Request(), Query {

    object Field {
        const val PAGE: String = "page"
    }

    override val query: RequestQuery
        get() = RequestQuery().apply {
            put(Field.PAGE, page.toString())
        }

}

private interface Query {
    val page: Int
}