package com.perpetua.eazytopup.apis

import com.perpetua.eazytopup.models.AirtimePurchaseResponse
import com.perpetua.eazytopup.models.DataForOthers
import com.perpetua.eazytopup.models.DataForSelf
import com.perpetua.eazytopup.models.DataPurchaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface DataTopupApi {
    @POST("api/app/self")
    suspend fun buyDataForSelf(@Body dataForSelf: DataForSelf): Response<DataPurchaseResponse>

    @POST("api/app/other")
    suspend fun buyDataForOther(@Body dataForOther: DataForOthers): Response<DataPurchaseResponse>
}
