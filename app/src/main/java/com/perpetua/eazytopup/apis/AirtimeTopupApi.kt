package com.perpetua.eazytopup.apis

import com.perpetua.eazytopup.models.AirtimeForOther
import com.perpetua.eazytopup.models.AirtimeForSelf
import com.perpetua.eazytopup.models.AirtimePurchaseResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AirtimeTopupApi {

    @POST("api/app/self")
    suspend fun buyAirtimeForSelf(@Body airtimeForSelf: AirtimeForSelf): Response<AirtimePurchaseResponse>

    @POST("api/app/other")
    suspend fun buyAirtimeForOther(@Body airtimeForOther: AirtimeForOther): Response<AirtimePurchaseResponse>
}