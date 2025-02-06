package com.example.listaperros.domain.repository

import com.example.listaperros.data.datasource.database.entities.DogEntity
import com.example.listaperros.domain.models.Dog

interface DogRepositoryInterface {
    fun getDogs() : List<Dog>
    fun getBreedDogs(breed:String) : List<Dog>

    /*
    Todas las operaciones con la BBDD, las haremos asíncronas.
    Por tanto le ponemos suspend.
     */
    suspend fun getDogsEntity() : List<Dog>
    suspend fun getBreedDogsEntity(breed:String) : List<Dog>


    suspend fun insertBreedEntitytoDatabase(listEntity : List<DogEntity>)
    suspend fun insertBreedEntitytoDatabase(dog : DogEntity)


    suspend fun deleteDatabase()
}