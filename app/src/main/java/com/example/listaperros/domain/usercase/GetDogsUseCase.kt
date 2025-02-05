package com.example.listaperros.domain.usercase

import com.example.listaperros.data.repository.InMemoryDogRepository
import com.example.listaperros.domain.models.Dog

class GetDogsUseCase(private val inMemoryDogRepository : InMemoryDogRepository) {

    suspend operator fun invoke(): List<Dog>?{
        return inMemoryDogRepository.getDogs()
    }
}