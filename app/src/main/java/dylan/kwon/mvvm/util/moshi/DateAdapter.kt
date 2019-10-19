package dylan.kwon.mvvm.util.moshi

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import dylan.kwon.mvvm.util.extension.*
import org.json.JSONException
import java.util.*

class DateAdapter {

    private val dateFormats: List<String> = listOf(
        DATE_TIME_FORMAT,
        DATE_FORMAT,
        TIME_FORMAT
    )

    @ToJson
    fun toJson(date: Date?): String {
        for (format: String in dateFormats) {
            try {
                return date format format

            } catch (e: Exception) {
                continue
            }
        }
        throw JSONException("Date is not matched format: $date")
    }

    @FromJson
    fun fromJson(date: String?): Date? {
        for (format: String in dateFormats) {
            try {
                return date parseDate format

            } catch (e: Exception) {
                continue
            }
        }
        throw JSONException("Date is not matched format: $date")
    }

}
