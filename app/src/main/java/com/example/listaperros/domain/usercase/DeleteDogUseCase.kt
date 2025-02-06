package com.example.listaperros.domain.usercase

import com.example.listaperros.data.repository.DogRepository
import javax.inject.Inject

class DeleteDogUseCase@Inject constructor(
    private val dogRepositoryDao: DogRepository
){

}