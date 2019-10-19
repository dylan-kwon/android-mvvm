package dylan.kwon.mvvm.data.local.db.dao

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dylan.kwon.mvvm.data.local.db.AppDatabase
import dylan.kwon.mvvm.data.local.db.entity.UserEntity
import dylan.kwon.mvvm.util.LogUtil
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class UserDaoTest : KoinComponent {

    companion object {
        @JvmField
        val TAG: String = UserDaoTest::class.java.simpleName
    }

    lateinit var appContext: Context

    val appDatabase: AppDatabase by inject()

    @After
    fun setUp() {
        appContext = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun insertUsers() {
        for (i: Long in 0L until 100L) {
            Completable.fromCallable {
                appDatabase.userDao.insert(
                    UserEntity(
                        id = (i + 1),
                        name = "db_user${i + 1}",
                        phone = "010361${String.format("%05d", (i + 1))}",
                        email = "dylan-kwon${String.format("%03d", (i + 1))}@gmail.com"
                    )
                )
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    LogUtil.e(TAG, "data in the user table has been inserted.")
                }, {
                    it.printStackTrace()
                })
        }
    }

    @Ignore("ignore")
    fun deleteAll() {
        Completable.fromCallable {
            appDatabase.userDao.deleteAll()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                LogUtil.e(TAG, "All data in the user table has been deleted.")
            }, {
                it.printStackTrace()
            })
    }

}
