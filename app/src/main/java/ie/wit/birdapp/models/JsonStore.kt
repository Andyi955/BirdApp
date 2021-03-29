package ie.wit.birdapp.models
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import ie.wit.birdapp.helpers.exists
import ie.wit.birdapp.helpers.read
import ie.wit.birdapp.helpers.write
import org.jetbrains.anko.AnkoLogger
import java.util.*


/**
 *
 * saves game to the json file
 *
 */


val JSON_FILE = "storage.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<BirdModel>>() {}.type

fun generateRandomId(): String? {
    return Random().toString()
}

class JSONStore : BirdStore, AnkoLogger {

    val context: Context
    var birds = mutableListOf<BirdModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<BirdModel> {
        return birds
    }

    override fun findById(id: Long): BirdModel? {
        TODO("Not yet implemented")
    }

    override fun create(bird1: BirdModel) {
        bird1.uid = generateRandomId()
        birds.add(bird1)
        serialize()
    }


    override fun update(bird1: BirdModel) {
        var foundBird : BirdModel? = birds.find { p -> p.uid == bird1.uid }
        if (foundBird != null) {
            foundBird.name = bird1.name
            foundBird.type = bird1.type

            serialize()
        }
    }
    override fun delete(bird1: Long) {
        birds.remove(bird1)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(birds, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        birds = Gson().fromJson(jsonString, listType)
    }
}