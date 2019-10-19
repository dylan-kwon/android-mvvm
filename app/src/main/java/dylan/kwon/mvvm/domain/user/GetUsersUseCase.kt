package dylan.kwon.mvvm.domain.user

import dylan.kwon.mvvm.data.remote.request.RequestGetUsers
import dylan.kwon.mvvm.data.remote.response.ResponseUsers
import dylan.kwon.mvvm.data.repository.UserRepository
import dylan.kwon.mvvm.util.base.livedata.UseCase
import dylan.kwon.mvvm.util.base.model.Response
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class GetUsersUseCase(

    private val userRepo: UserRepository

) : UseCase<RequestGetUsers, ResponseUsers>() {

    override fun request(
        request: RequestGetUsers,
        onResponse: (ResponseUsers) -> Unit
    ): Disposable = userRepo.getUsers(request)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .onBackpressureBuffer()
        .subscribeBy(
            onNext = {
                onResponse(it)
            },
            onError = {
                onResponse(ResponseUsers().apply {
                    code = Response.Code.NETWORK_ERROR
                })
            }
        )
}