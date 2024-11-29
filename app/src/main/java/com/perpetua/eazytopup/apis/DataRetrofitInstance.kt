package com.perpetua.eazytopup.apis

import com.perpetua.eazytopup.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataRetrofitInstance {
    companion object{
        private val retrofit by lazy {
            //logging responses with HttpLoggingInterceptor
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            //okhttp client using the interceptor
            val client = OkHttpClient.Builder()
                .hostnameVerifier { hostname, _ -> hostname == Constants.DOMAIN_NAME }
                .addInterceptor(logging)
                .build()

            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val api by lazy {
            //retrofit.create(AirtimeTopupApi::class.java)
            retrofit.create(DataTopupApi::class.java)
        }
    }
}