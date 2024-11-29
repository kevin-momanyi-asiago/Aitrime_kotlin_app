package com.perpetua.eazytopup.repositories

import com.perpetua.eazytopup.apis.DataRetrofitInstance
import com.perpetua.eazytopup.apis.RetrofitInstance
import com.perpetua.eazytopup.models.AirtimeForOther
import com.perpetua.eazytopup.models.AirtimeForSelf
import com.perpetua.eazytopup.models.DataForOthers
import com.perpetua.eazytopup.models.DataForSelf

class DataRepository {
    suspend fun buyDataForSelf(dataForSelf: DataForSelf) =
        DataRetrofitInstance.api.buyDataForSelf(dataForSelf)


    suspend fun buyDataForOther(dataForOthers: DataForOthers)=
        DataRetrofitInstance.api.buyDataForOther(dataForOthers)

}