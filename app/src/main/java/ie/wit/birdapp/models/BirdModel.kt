package ie.wit.birdapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate

@Parcelize
data class BirdModel(var id: Long = 0,
                     var name: String = "NA",
                     var ref: Long = 0,
                     var type: String = "NA"   ) : Parcelable
