package ie.wit.birdapp.main

import android.app.Application
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import ie.wit.birdapp.models.*

class BirdApp : Application() {
    lateinit var birdStore: BirdStore
    var birds = ArrayList<BirdModel>()

    // [START declare_auth]
    lateinit var auth: FirebaseAuth
    // [END declare_auth]

    override fun onCreate() {
        super.onCreate()
        birdStore = JSONStore(applicationContext)

        Log.v("Birds","Bird Watching App started")
    }
}