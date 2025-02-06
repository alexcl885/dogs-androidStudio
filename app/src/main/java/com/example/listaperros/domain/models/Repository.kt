package com.example.listaperros.domain.models

import com.example.listaperros.data.datasource.database.entities.DogEntity

class Repository {
    companion object{
        var dogs:List<Dog> = emptyList()
    }
}