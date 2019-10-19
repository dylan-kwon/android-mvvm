package dylan.kwon.mvvm.util.base.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ParcelableString(

    var string: String = ""

) : Parcelable {

    override fun toString(): String {
        return string
    }

}
