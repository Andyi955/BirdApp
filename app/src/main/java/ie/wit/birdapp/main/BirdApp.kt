package ie.wit.birdapp.main

import android.app.Application
import android.util.Log
import ie.wit.birdapp.models.*

class BirdApp : Application() {
    lateinit var birdStore: BirdStore

    override fun onCreate() {
        super.onCreate()
        birdStore = JSONStore(applicationContext)

        Log.v("Birds","Bird Watching App started")
    }
}