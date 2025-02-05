package com.example.listaperros.ui.modelview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listaperros.data.repository.InMemoryDogRepository
import com.example.listaperros.domain.models.Dog
import com.example.listaperros.domain.usercase.GetDogsBreedUseCase
import com.example.listaperros.domain.usercase.GetDogsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DogViewModel : ViewModel() {
    var dogListLiveData = MutableLiveData<List<Dog>>() //repositorio observable
    var progressBarLiveData = MutableLiveData<Boolean> () //progressbar observable
    var search = MutableLiveData<String>() //para el campo search

    lateinit var useCaseList : GetDogsUseCase
    lateinit var useCaseBreedList : GetDogsBreedUseCase


    /*
    Si no especificamos nada en el viewModelScop.launch{} todo se ejecuta en el hilo principal
    Hasta que el bloque withContext no se complete, el flujo de ejecución se suspende, hasta que
    no finalize la consulta de datos en otro hilo.
     */
    fun list() {
        viewModelScope.launch {  //Se destruye al eliminarse el ViewModel
            progressBarLiveData.value = true //notifico
            // delay(2000)
            var data : List<Dog>?
            withContext(Dispatchers.IO){
                useCaseList = GetDogsUseCase(InMemoryDogRepository())
                data  = useCaseList()  //aquí se invoca y se obtienen los datos.
            }
            //Se suspende este flujo, a la espera de la operación asíncrona en otro hilo.
            //La UI puede seguir trabajando sin problemas.
            if (data !=null )
                dogListLiveData.value = data!!  //notifico
            progressBarLiveData.value = false  //notifico
        }
    }


    fun list1() {
        viewModelScope.launch {  //Se destruye al eliminarse el ViewModel
            progressBarLiveData.value = true //notifico
            // delay(2000)
            var data : List<Dog>?
            withContext(Dispatchers.IO){
                useCaseList = GetDogsUseCase(InMemoryDogRepository())
                data  = useCaseList()  //aquí se invoca y se obtienen los datos.
                if (data !=null )
                    dogListLiveData.postValue(data!!)  //notifico
                progressBarLiveData.postValue(false)
            }
        }
    }




    fun searchByBreed(breed: String){
        //Log.i("TAG-DOGS", "La raza elegida es $breed")
        search.value = breed  //notificamos cambio
    }

    fun listForBreed(breed: String) {
        viewModelScope.launch {
            progressBarLiveData.value = true //notifico
            // delay(2000)
            var data : List<Dog>?
            withContext(Dispatchers.IO){
                useCaseBreedList = GetDogsBreedUseCase(InMemoryDogRepository(), breed)
                data = useCaseBreedList()  //aquí se invoca y se obtienen los datos.
            }

            if (data != null)
                dogListLiveData.value = data!!  //notifico
            progressBarLiveData.value = false  //notifico

        }
    }

}