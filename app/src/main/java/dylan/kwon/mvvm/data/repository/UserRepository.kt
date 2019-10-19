package dylan.kwon.mvvm.data.repository

import dylan.kwon.mvvm.data.local.db.AppDatabase
import dylan.kwon.mvvm.data.local.db.dao.UserDao
import dylan.kwon.mvvm.data.local.db.entity.UserEntity
import dylan.kwon.mvvm.data.remote.api.UserApi
import dylan.kwon.mvvm.data.remote.dto.User
import dylan.kwon.mvvm.data.remote.request.RequestGetUsers
import dylan.kwon.mvvm.data.remote.response.ResponseUser
import dylan.kwon.mvvm.data.remote.response.ResponseUsers
import dylan.kwon.mvvm.util.base.model.Response
import io.reactivex.Flowable

class UserRepository(

    private val userApi: UserApi,
    private val userDao: UserDao

) {

    fun getUsers(requestGetUser: RequestGetUsers): Flowable<ResponseUsers> = userApi.getUsers(requestGetUser.query)
        .onErrorResumeNext { t: Throwable ->
            t.printStackTrace()

            val count: Int = AppDatabase.COUNT_PER_PAGE
            val startIndex: Int = (requestGetUser.page - 1) * count

            return@onErrorResumeNext userDao.getUsers(startIndex, count)
                .map { users ->
                    return@map ResponseUsers().also { response ->
                        response.code = Response.Code.SUCCESS
                        response.data = ResponseUsers.Data().also { data ->
                            data.users = users.toDto()
                            data.isPageEnd = users.size < AppDatabase.COUNT_PER_PAGE
                        }
                    }
                }
        }

    fun getUser(userId: Int): Flowable<ResponseUser> = userApi.getUser(userId)
        .onErrorResumeNext { it: Throwable ->
            it.printStackTrace()
            return@onErrorResumeNext userDao.getUser(userId)
                .map {
                    return@map ResponseUser().also { response ->
                        response.code = Response.Code.SUCCESS
                        response.data = ResponseUser.Data().also { data ->
                            data.user = it.toDto()
                        }
                    }
                }
        }

    private fun List<UserEntity>.toDto(): MutableList<User> = mutableListOf<User>().also {
        for (userEntity: UserEntity in this) {
            it += userEntity.toDto()
        }
    }

}