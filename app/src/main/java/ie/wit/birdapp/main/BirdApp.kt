package ie.wit.birdapp.main

import android.app.Application
import android.location.Location
import android.net.Uri
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import ie.wit.birdapp.models.*

class BirdApp : Application() {
//    lateinit var birdStore: BirdStore
    var birds = ArrayList<BirdModel>()

    // [START declare_auth]
    lateinit var currentUser: FirebaseUser
    // [END declare_auth]
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var storage: StorageReference
    lateinit var database: DatabaseReference
    lateinit var userImage: Uri
    lateinit var currentLocation : Location
    lateinit var locationClient : FusedLocationProviderClient
    lateinit var mMap : GoogleMap
    lateinit var marker : Marker

    override fun onCreate() {
        super.onCreate()
       // birdStore = JSONStore(applicationContext)

        Log.v("Birds","Bird Watching App started")
    }
}