package com.example.listaperros.data.service

import com.example.listaperros.data.datasource.Dogs
import kotlinx.coroutines.delay

class DogService  {
    suspend fun getDogs(): List<Pair<String, String>> {
        delay(1000)  // Simulando un retardo de red (como si estuviera esperando una respuesta de la API)
        return Dogs.dogs
    }

    /*
    Aquí, estoy filtrando por raza. Simulo un acceso por filtro.
     */
    suspend fun getBreedDogs(breed: String): List<Pair<String,String>> {
        delay(1000)
        val newDogs = Dogs.dogs.filter {
            it.first.equals(breed)
        }
        return newDogs
    }

}