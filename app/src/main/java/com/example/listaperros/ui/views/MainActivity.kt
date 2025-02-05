package com.example.listaperros.ui.views

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listaperros.R
import com.example.listaperros.databinding.ActivityMainBinding
import com.example.listaperros.ui.adapter.DogAdapter
import com.example.listaperros.ui.modelview.DogViewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
    androidx.appcompat.widget.SearchView.OnQueryTextListener {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: DogAdapter
    val dogViewModel: DogViewModel by viewModels() //tiene que ser constante.


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.mySearch.setOnQueryTextListener(this)  //cargamos el listener
        registerLiveData()  //Observamos cambios.
        loadDada() //se cargan todos los datos.
        initRecyclerView()  //inicializamos el recyclerView.
        //test()
    }

    private fun loadDada() {
        dogViewModel.list()  //simulamos un evento para iniciar la carga de datos desde el viewmodel

    }

    /*
    Quiero suscribir al activity, cuando los datos de dogListLiveData,
    cambién. En el momento que haya ese cambio, el ViewModel notificará
    al activity y ejecutará la lambda.
     */
    private fun registerLiveData() {
        dogViewModel.dogListLiveData.observe(
            this, {  myList->
                //Aquí hacemos la actualización del adapter.
                adapter.dogRepository = myList!!  //aseguro los datos.
                binding.myRecyclerPpal.adapter = adapter  //le asigno el adapter.
                adapter.notifyDataSetChanged()  //No hace falta, pero por si acaso.
            })

        /*
        Cuando exista un cambio en el modelo, quiero que el Activity
        sea notificado para que actualize la ui.
         */
        dogViewModel.progressBarLiveData.observe(
            this, { visible ->
                binding.progressBar.isVisible = visible
                Log.i("TAG-DOGS","ProgressBar esta $visible")            }
        )

        /*
        Observamos un cambio en el search.
         */
        dogViewModel.search.observe(  //el campo search, ha cambiado
            this, { bread->
                dogViewModel.listForBreed(bread)  //cambiamos los datos.
                hideKeyBoard()
            }
        )
    }

    /*
    Pone todo en funcionamiento
     */
    private fun initRecyclerView(){
        binding.myRecyclerPpal.layoutManager = LinearLayoutManager(this)
        adapter = DogAdapter()
    }


    /*
    Este método, es llamado cuando se escribe algo en el campo y se pulsa.
     */
    override fun onQueryTextSubmit(query: String?): Boolean {

        if (!query.isNullOrEmpty())
            dogViewModel.searchByBreed(query!!)
        return true
    }

    /*
    Cualquier cambio, llamará a este método. Estoy obligado a ponerlo
    Principalmente, lo utilizo para cargar toda la lista de nuevo, al
    estar el campo vacío.
     */
    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText.isNullOrEmpty()) {
            dogViewModel.list()
            hideKeyBoard()
        }
        return true
    }



    /*
    Método que cierra el teclado. MUY INTERESANTE...
     */
    private fun hideKeyBoard() {
        val imn = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imn.hideSoftInputFromWindow(binding.myLayoutPpal.windowToken, 0)
    }

    private fun test() {
        //  TestApi.testDogApi()
    }
}