package com.example.listaperros.data.service

import com.example.listaperros.data.datasource.mem.models.Dogs
import kotlinx.coroutines.delay



import javax.inject.Inject


class DogService @Inject constructor() {
    suspend fun getDogs(): List<Pair<String, String>> {
        delay(1000)
        return Dogs.dogs
    }

    suspend fun getBreedDogs(breed: String): List<Pair<String,String>> {
        delay(1000)
        return Dogs.dogs.filter { it.first.equals(breed, ignoreCase = true) }
    }
}
