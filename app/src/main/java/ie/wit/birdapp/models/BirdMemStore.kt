package ie.wit.birdapp.models

import android.util.Log

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class BirdMemStore : BirdStore {

    val birds = ArrayList<BirdModel>()

    override fun findAll(): List<BirdModel> {
        return birds
    }

    override fun findById(id:Long) : BirdModel? {
        val foundBird: BirdModel? = birds.find { it.id == id }
        return foundBird
    }

    override fun create(bird1: BirdModel) {
        bird1.id = getId()
        birds.add(bird1)
        logAll()
    }

    override fun delete(bird1: BirdModel) {
        birds.remove(bird1)
    }

    override fun update(bird1: BirdModel) {
        TODO("Not yet implemented")
    }

    fun logAll() {
        Log.v("Donate","** Donations List **")
        birds.forEach { Log.v("Donate","${it}") }
    }
}