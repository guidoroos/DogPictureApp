package com.example.dogsapplication.api

import com.example.dogsapplication.database.DogDatabase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


val apiModule = module {
    //moshi for json serialization/deserialization
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    //retrofit builder
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


//interface used for request: here get request using filter
interface DogApiService {
    /**
     * Returns a Coroutine [List] of [MarsProperty] which can be fetched with await() if in a Coroutine scope.
     * The @GET annotation indicates that the "realestate" endpoint will be requested with the GET
     * HTTP method
     */
    /**
     * Returns a Coroutine [List] of [MarsProperty] which can be fetched with await() if in a Coroutine scope.
     * The @GET annotation indicates that the "realestate" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("breeds/image/random")
    suspend fun getRandomDog(): DogResponse
}

