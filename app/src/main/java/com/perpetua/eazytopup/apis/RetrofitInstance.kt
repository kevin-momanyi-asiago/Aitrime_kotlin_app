package com.perpetua.eazytopup.apis

import androidx.recyclerview.widget.DiffUtil
import com.perpetua.eazytopup.models.Contact
import com.perpetua.eazytopup.utils.Constants.Companion.BASE_URL
import com.perpetua.eazytopup.utils.Constants.Companion.DOMAIN_NAME
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection


class RetrofitInstance {
    companion object{
        private val retrofit by lazy {
            //logging responses with HttpLoggingInterceptor
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            //okhttp client using the interceptor
            val client = OkHttpClient.Builder()
                .hostnameVerifier { hostname, _ -> hostname == DOMAIN_NAME }
                .addInterceptor(logging)
                .build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val api by lazy {
            retrofit.create(AirtimeTopupApi::class.java)
            //retrofit.create(DataTopupApi::class.java)
        }
    }
}