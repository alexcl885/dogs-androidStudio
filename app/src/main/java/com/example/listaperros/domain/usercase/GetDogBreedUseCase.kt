package com.example.listaperros.domain.usercase

import com.example.listaperros.data.datasource.database.entities.DogEntity
import com.example.listaperros.data.repository.DogRepository
import com.example.listaperros.domain.mapper.toDogEntity
import com.example.listaperros.domain.models.Dog
import com.example.listaperros.domain.models.Repository
import javax.inject.Inject

class GetDogsBreedUseCase @Inject constructor(
    private val dogRepositoryDao: DogRepository
){
    private var breed: String = ""
    fun setBreed(breed: String){
        this.breed = breed
    }


    suspend operator fun invoke() : List<Dog>{
        Repository.dogs = dogRepositoryDao.getBreedDogsEntity(breed)  //Aquí tengo los datos.

        if (Repository.dogs.isEmpty()){
            Repository.dogs  = dogRepositoryDao.getBreedDogs(breed) //Aquí tengo los datos de memoria.
            val dataDogEntity : List<DogEntity> = Repository.dogs.map { it.toDogEntity() }  //lo mapeamos a Entity.
            dogRepositoryDao.insertBreedEntitytoDatabase(dataDogEntity)
        }
        return  return Repository.dogs

    }

}