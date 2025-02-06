package com.example.listaperros.data.repository

import com.example.listaperros.data.datasource.database.dao.DogDao
import com.example.listaperros.data.datasource.database.entities.DogEntity
import com.example.listaperros.data.datasource.mem.service.DogService
import com.example.listaperros.domain.mapper.toDog
import com.example.listaperros.domain.mapper.toDogEntity
import com.example.listaperros.domain.models.Dog
import com.example.listaperros.domain.models.Repository
import com.example.listaperros.domain.repository.DogRepositoryInterface
import javax.inject.Inject

class DogRepository @Inject constructor(
    private val service : DogService,
    private val dogDao : DogDao
) : DogRepositoryInterface {
    //private val service : DogService = DogService()

    /*
    Método que a partir de los datos nativos, devuelve la lista
    de objetos que necesita el modelo.
     */
    override fun getDogs(): List<Dog> {
        val dataSource = service.getDogs()

        Repository.dogs = dataSource.map { it.toDog() }
        return Repository.dogs

        /*
        dataSource.forEach{ dog->
            mutableDogs.add(Dog(dog.first, dog.second))
        }
        Repository.dogs = mutableDogs //AQUÍ CARGO LOS DATOS EN MEMORIA.
        return Repository.dogs
         */
    }

    override fun getBreedDogs(breed: String): List<Dog> {
        val dataSource = service.getBreedDogs(breed)

        Repository.dogs = dataSource.map { it.toDog() }
        return Repository.dogs
        /*
        dataSource.forEach{ dog->
            mutableDogs.add(Dog(dog.first, dog.second))

        }
        Repository.dogs = mutableDogs //AQUÍ CARGO LOS DATOS EN MEMORIA.
        return Repository.dogs
         */
    }

    override suspend fun getDogsEntity(): List<Dog> {
        val listEntity : List<DogEntity> = dogDao.getAll()  //aquí tengo todos los datos Dog
        Repository.dogs = listEntity.map { it.toDogEntity()}  //convertimos a Dog (dominio) y lo cargamos en memoria
        return Repository.dogs

    }

    override suspend fun getBreedDogsEntity(breed: String): List<Dog> {
        val listEntity : List<DogEntity> = dogDao.getDogsByBreed(breed)  //aquí tengo todos los datos Entity filtrados
        Repository.dogs = listEntity.map { it.toDogEntity()}  //convertimos a DogModel (dominio) y lo cargamos en memoria
        return Repository.dogs

    }

    /*
    Necesito un método que inserte una lista de Entity en la BBDD.
     */
    override suspend fun insertBreedEntitytoDatabase(listEntity : List<DogEntity>) {
        dogDao.insertAllDog(listEntity)
    }

    override suspend fun insertBreedEntitytoDatabase(dog: DogEntity) {
        dogDao.insertDog(dog)
    }

    override suspend fun deleteDog(dog: DogEntity) {
        dogDao.deleteDog(dog.breed)
    }

    override suspend fun deleteDatabase() {
        dogDao.deleteAll()
    }

}