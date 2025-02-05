package com.example.listaperros.domain.repository

import com.example.listaperros.domain.models.Dog

interface DogRepositoryInterface {
    suspend fun getDogs() : List<Dog>   //deberá devolver una lista de Dog (dominio)
    suspend fun getBreedDogs(breed:String) : List<Dog>  //deberá devolver una lista por raza de Dog (dominio)
}