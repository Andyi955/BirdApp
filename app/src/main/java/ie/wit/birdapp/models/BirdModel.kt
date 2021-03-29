package ie.wit.birdapp.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate

@Parcelize
data class BirdModel(var uid: String? = "",
                    // var id: Long = 0,
                     var name: String = "NA",
                     var ref: Long = 0,
                     var type: String = "NA",
                     var email: String? ="andy@andy.com") : Parcelable


{
    @Exclude
    fun toMap(): Map<String, Any?>{
        return mapOf(

            "uid" to uid,
            "name" to name,
            "ref" to ref,
            "type" to type,
            "email" to email

        )


    }




}