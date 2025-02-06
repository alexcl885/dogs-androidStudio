package com.example.listaperros.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.listaperros.databinding.ItemDogBinding
import com.example.listaperros.domain.models.Dog


/*
Se encargará de inicializar las vistas, elemento por elemento. Pondrá todos los datos que le pasa el adaptador.
 */
class ViewHDog(view: View, var deleteOnClick: (Int) -> Unit) : RecyclerView.ViewHolder(view){

    private lateinit var binding: ItemDogBinding

    init {
        binding = ItemDogBinding.bind(view)
    }


    /*
    Métdo que se encara de mapear los item por propiedad del modelo.
     */
    fun rendereize(get: Dog, position: Int) {
        Glide
            .with(itemView.context)
            .load(get.image)
            .centerCrop()
            .into(binding.ivImagen)

        setOnClickListener(position)
    }
    private fun setOnClickListener(position : Int) {
        binding.btDelete.setOnClickListener{
            deleteOnClick(position)
        }

    }

}