package com.pbvitorlucas.myapplication.service

import android.content.Context
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class RetroFit {
    companion object {
        fun retrofit(context: Context): Retrofit = Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create()).client(okhttpClient(context)).build()
        fun pokemonsService(context: Context) = retrofit(context).create(PokemonsServices::class.java)

        fun provideCacheInterceptor(): Interceptor {
            return Interceptor { chain ->
                var request: Request = chain.request()
                val originalResponse: Response = chain.proceed(request)
                val cacheControl: String? = originalResponse.header("Cache-Control")
                if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                    cacheControl.contains("must-revalidate") || cacheControl.contains("max-stale=0")
                ) {
                    val cc = CacheControl.Builder()
                        .maxStale(1, TimeUnit.DAYS)
                        .build()
                    request = request.newBuilder()
                        .removeHeader("Pragma")
                        .cacheControl(cc)
                        .build()
                    chain.proceed(request)
                } else {
                    originalResponse

                }
            }
        }

        fun offlineCacheInterceptor(): Interceptor {
            return Interceptor { chain ->
                try {
                    return@Interceptor chain.proceed(chain.request())
                } catch (e: Exception) {
                    val cacheControl = CacheControl.Builder()
                        .onlyIfCached()
                        .maxStale(30, TimeUnit.DAYS)
                        .build()
                    val offlineRequest: Request = chain.request().newBuilder()
                        .cacheControl(cacheControl)
                        .removeHeader("Pragma")
                        .build()
                    return@Interceptor chain.proceed(offlineRequest)
                }
            }
        }

        fun okhttpClient(context: Context): OkHttpClient {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val httpCacheDirectory = File(context.cacheDir, "offlineCache")
            val cacheSize = (2000 * 1024 * 1024).toLong() //
            val cache = Cache(httpCacheDirectory, cacheSize)

            val okHttpClient: OkHttpClient =
                OkHttpClient.Builder() // .addInterceptor(provideHttpLoggingInterceptor()) // For HTTP request & Response data logging
                    .cache(cache)
                    .addInterceptor(httpLoggingInterceptor)
                    .addNetworkInterceptor(provideCacheInterceptor())
                    .addInterceptor(offlineCacheInterceptor())
                    .build()
            return okHttpClient
        }
    }
}