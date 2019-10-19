package dylan.kwon.mvvm.data.remote.api

import dylan.kwon.mvvm.data.remote.response.ResponseUser
import dylan.kwon.mvvm.data.remote.response.ResponseUsers
import dylan.kwon.mvvm.util.base.model.RequestQuery
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface UserApi {

    @GET("v1/users")
    fun getUsers(
        @QueryMap query: RequestQuery
    ): Flowable<ResponseUsers>

    @GET("v1/users/{user_id}")
    fun getUser(
        @Path("user_id") userId: Int
    ): Flowable<ResponseUser>

}