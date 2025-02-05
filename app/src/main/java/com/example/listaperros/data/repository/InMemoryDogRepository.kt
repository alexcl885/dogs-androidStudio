package com.example.listaperros.data.repository

import com.example.listaperros.data.service.DogService
import com.example.listaperros.domain.models.Dog
import com.example.listaperros.domain.models.DogsData
import com.example.listaperros.domain.repository.DogRepositoryInterface

class InMemoryDogRepository : DogRepositoryInterface {
    private val service : DogService = DogService()  //simula las peticiones a la API, pero será memoria.

    /*
    Método que a partir de los datos nativos, devuelve la lista
    de objetos que necesita el modelo.
     */
    override  suspend fun getDogs(): List<Dog> {
        var mutableDogs : MutableList<Dog> = mutableListOf()
        val dataSource = service.getDogs()  //aquí tenemos la lista de Dog nativos. Pair.
        dataSource.forEach{ dogPair->
            mutableDogs.add(Dog(dogPair.first, dogPair.second)) //para todos los Pair --> creamos Dog(modelo del dominio)
        }
        DogsData.dogs = mutableDogs //AQUÍ CARGO LOS DATOS EN MEMORIA.
        return DogsData.dogs
    }

    /*
    Cargo todos los datos filtrados por raza, desde nuestra lista Pair (datos nativos) a memoria.
     */
    override suspend fun getBreedDogs(breed: String): List<Dog> {
        var mutableDogs : MutableList<Dog> = mutableListOf()
        val dataSource = service.getBreedDogs(breed)
        dataSource.forEach{ dogPair->
            mutableDogs.add(Dog(dogPair.first, dogPair.second))
        }
        DogsData.dogs = mutableDogs //AQUÍ CARGO LOS DATOS EN MEMORIA.
        return DogsData.dogs
    }
}