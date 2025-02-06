package com.example.listaperros.domain.mapper

import com.example.listaperros.data.datasource.database.entities.DogEntity
import com.example.listaperros.domain.models.Dog

//Necesitamos mapear un DogEntity a DogModel BBDD  --> IU
fun DogEntity.toDogEntity(): Dog {
    return Dog(breed = this.breed, image = this.image)
}



//Necesitamos mapear un DogModel a DogEntity para persistir  IU --> BBDD
fun Dog.toDogEntity(): DogEntity{
    return DogEntity(breed = breed, image = image)
}

//Necesitamos mapear un Pair<String,String> a Dog  Pair --> IU
fun Pair<String, String>.toDog(): Dog {
    return Dog(breed = first, image = second)
}