package ie.wit.birdapp.models

interface BirdStore {
    fun findAll() : List<BirdModel>
    fun findById(id: Long) : BirdModel?
    fun create(bird1: BirdModel)
    fun delete(bird1: Long)
    fun update(bird1: BirdModel)
}