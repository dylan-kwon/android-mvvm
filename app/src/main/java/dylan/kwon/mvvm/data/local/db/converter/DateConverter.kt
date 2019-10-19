package dylan.kwon.mvvm.data.local.db.converter

import androidx.room.TypeConverter
import dylan.kwon.mvvm.util.extension.DATE_TIME_FORMAT
import dylan.kwon.mvvm.util.extension.format
import dylan.kwon.mvvm.util.extension.parseDate
import java.util.*

object DateConverter {

    @JvmStatic
    @TypeConverter
    fun dateToString(date: Date): String = date format DATE_TIME_FORMAT

    @JvmStatic
    @TypeConverter
    fun stringToDate(date: String): Date? = date parseDate DATE_TIME_FORMAT

}
