package com.example.listaperros.domain.usercase

import com.example.listaperros.data.repository.InMemoryDogRepository
import com.example.listaperros.domain.models.Dog

class GetDogsBreedUseCase(val inMemoryDogRepository : InMemoryDogRepository,
                          val breed: String) {

    suspend operator fun invoke() : List<Dog>{
        return inMemoryDogRepository.getBreedDogs(breed)
    }

}