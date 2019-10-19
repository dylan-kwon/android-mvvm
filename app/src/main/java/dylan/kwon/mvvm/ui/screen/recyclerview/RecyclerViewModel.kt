package dylan.kwon.mvvm.ui.screen.recyclerview

import androidx.lifecycle.LiveData
import dylan.kwon.mvvm.data.remote.dto.User
import dylan.kwon.mvvm.data.remote.request.RequestGetUsers
import dylan.kwon.mvvm.data.remote.response.ResponseUsers
import dylan.kwon.mvvm.domain.user.GetUsersUseCase
import dylan.kwon.mvvm.util.base.livedata.StateLiveData
import dylan.kwon.mvvm.util.base.viewmodel.BaseViewModel
import io.reactivex.rxkotlin.plusAssign

class RecyclerViewModel(

    private val getUsersUseCase: GetUsersUseCase

) : BaseViewModel(), RecyclerViewHolder.ViewModel {

    private val _users: StateLiveData<MutableList<User>> = StateLiveData()
    val users: LiveData<MutableList<User>>
        get() = _users

    fun setUsers(users: MutableList<User>?) {
        _users.value = users
    }

    fun requestGetUsers() {
        showProgress()
        val request: RequestGetUsers = RequestGetUsers(
            page = 1
        )
        this.compositeDisposable += getUsersUseCase.request(request) { response ->
            onResponseGetUsers(response)
        }
    }

    private fun onResponseGetUsers(response: ResponseUsers) = response.check(
        onSuccess = {
            setUsers(response.data?.users)
        },
        onFail = {
            when (response.isNetworkError) {
                true -> showNetworkError()
                else -> showToast(response.message)
            }
        }
    ).let {
        dismissProgress()
    }

    override fun printUserInfo(user: User?) {
        val message: StringBuilder = StringBuilder().apply {
            when (user) {
                null -> append("null")
                else -> {
                    append("user.name = ${user.name}")
                    append("\n")
                    append("user.phone = ${user.phone}")
                    append("\n")
                    append("user.email = ${user.email}")
                }
            }
        }
        showToast(message.toString())
    }
}