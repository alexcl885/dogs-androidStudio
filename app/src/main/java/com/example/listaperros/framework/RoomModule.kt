package com.example.listaperros.framework

import android.content.Context
import androidx.room.Room
import com.example.listaperros.data.datasource.database.DatabaseDogs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module  //Hilt, que este object contiene métodos de cómo crear dependencias, ehhhhhh
@InstallIn(SingletonComponent::class)  //Hilt, que estos objetos duren hasta el cierre de la app.
object RoomModule {

    private const val NAME_DATABASE = "database_dogs"


    @Singleton   //Hilt, que quiero que me crees una sola instancia, por eso te digo que es singleton.
    @Provides   //Hilt, que quiero darte información a cómo instanciar DatabaseDogs
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, DatabaseDogs::class.java, NAME_DATABASE).build()



    @Singleton  //Hilt, que quiero que me crees una sola instancia, por eso te digo que es singleton.
    @Provides   //Hilt, que quiero darte información a cómo debes instanciar el dao.
    fun provideDao(database: DatabaseDogs) = database.dogDao()
}