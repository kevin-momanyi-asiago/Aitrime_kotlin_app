package com.perpetua.eazytopup.repositories

import com.perpetua.eazytopup.apis.RetrofitInstance
import com.perpetua.eazytopup.models.AirtimeForOther
import com.perpetua.eazytopup.models.AirtimeForSelf
import retrofit2.awaitResponse

class AirtimeRepository {
    suspend fun buyAirtimeForSelf(airtimeForSelf: AirtimeForSelf) =
        RetrofitInstance.api.buyAirtimeForSelf(airtimeForSelf)


    suspend fun buyAirtimeForOther(airtimeForOther: AirtimeForOther)=
         RetrofitInstance.api.buyAirtimeForOther(airtimeForOther)

}