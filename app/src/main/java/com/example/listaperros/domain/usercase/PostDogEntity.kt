package com.example.listaperros.domain.usercase

import com.example.listaperros.data.repository.DogRepository
import com.example.listaperros.domain.mapper.toDogEntity
import com.example.listaperros.domain.models.Dog
import javax.inject.Inject

class PostDogEntity @Inject constructor(
     val dogRepositoryDao: DogRepository
) {
    var dog : Dog? = null
    suspend operator fun invoke(dog: Dog): Boolean{
        return if (this.dog != null){
            this.dog?.let { dogRepositoryDao.insertBreedEntitytoDatabase(it.toDogEntity()) }
            true
        }
        else{
            false
        }
    }
}