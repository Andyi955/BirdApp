package ie.wit.birdapp.main

import android.app.Application
import android.net.Uri
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import ie.wit.birdapp.models.*

class BirdApp : Application() {
//    lateinit var birdStore: BirdStore
    var birds = ArrayList<BirdModel>()

    // [START declare_auth]
    lateinit var auth: FirebaseAuth
    // [END declare_auth]
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var storage: StorageReference
    lateinit var database: DatabaseReference
    lateinit var userImage: Uri


    override fun onCreate() {
        super.onCreate()
       // birdStore = JSONStore(applicationContext)

        Log.v("Birds","Bird Watching App started")
    }
}