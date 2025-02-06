package com.example.listaperros.domain.usercase

import com.example.listaperros.data.repository.DogRepository
import com.example.listaperros.domain.mapper.toDogEntity
import com.example.listaperros.domain.models.Dog
import com.example.listaperros.domain.models.Repository
import javax.inject.Inject

class DeleteDogUseCase@Inject constructor(
    private val dogRepositoryDao: DogRepository
){
    var dog : Dog? = null
    suspend  operator  fun invoke(dog:Dog): Boolean{

        return if (this.dog != null){
            this.dog?.let { dogRepositoryDao.deleteDog(it.toDogEntity()) }
            true
        }
        else{
            false
        }
    }

}