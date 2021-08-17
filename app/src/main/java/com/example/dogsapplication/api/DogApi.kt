package com.example.dogsapplication.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


/**
 * Api module used in glide to generate a singleton dependency for the api service
 */
val apiModule = module {
    //moshi for json serialization/deserialization
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    fun provideRetrofit(moshi: Moshi): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl("https://dog.ceo/api/")
        .build()

    fun createDogApiService(retrofit: Retrofit): DogApiService =
        retrofit.create(DogApiService::class.java)

    single { provideMoshi()}
    single { provideRetrofit(get())}
    single { createDogApiService(get())}
}



interface DogApiService {
    /**
     * Returns a random dog object from the dog.ceo api
     */

    @GET("breeds/image/random")
    suspend fun getRandomDog(): DogResponse
}

