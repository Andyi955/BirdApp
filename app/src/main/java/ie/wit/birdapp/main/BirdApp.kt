package ie.wit.birdapp.main

import android.app.Application
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import ie.wit.birdapp.models.*

class BirdApp : Application() {
//    lateinit var birdStore: BirdStore
    var birds = ArrayList<BirdModel>()

    // [START declare_auth]
    lateinit var auth: FirebaseAuth
    // [END declare_auth]

    lateinit var database: DatabaseReference

    override fun onCreate() {
        super.onCreate()
       // birdStore = JSONStore(applicationContext)

        Log.v("Birds","Bird Watching App started")
    }
}