package com.perpetua.eazytopup.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perpetua.eazytopup.models.DataForOthers
import com.perpetua.eazytopup.models.DataForSelf
import com.perpetua.eazytopup.models.DataPurchaseResponse
import com.perpetua.eazytopup.repositories.DataRepository
import com.perpetua.eazytopup.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class DataViewModel(private val dataRepository: DataRepository) : ViewModel()  {
    val dataForSelfData: MutableLiveData<Resource<DataPurchaseResponse>> = MutableLiveData()
    val dataForOthersData: MutableLiveData<Resource<DataPurchaseResponse>> = MutableLiveData()

    init {
        Log.d("DataViewModel", "View model started")
    }

    fun buyDataForSelf(dataForSelf: DataForSelf) = viewModelScope.launch(Dispatchers.IO) {
        dataForSelfData.postValue(Resource.Loading())
        if (dataForSelf.number.isEmpty()) {
            dataForSelfData.postValue(Resource.PhoneNumberError("Phone Number missing"))
            return@launch
        }
        if (dataForSelf.pesa.isEmpty()) {
            dataForSelfData.postValue(Resource.AmountError("Amount missing"))
            return@launch
        }
        val response = dataRepository.buyDataForSelf(dataForSelf)
        Log.d("DataViewModel", "retrofit response for self: $response")
        dataForSelfData.postValue(handleDataResponse(response))
    }

    fun buyDataForOther(dataForOther: DataForOthers) = viewModelScope.launch(Dispatchers.IO) {
        dataForOthersData.postValue(Resource.Loading())
        if (dataForOther.number.isEmpty()) {
            dataForOthersData.postValue(Resource.PhoneNumberError("Phone Number missing"))
            return@launch
        }
        if (dataForOther.msisdn.isEmpty()) {
            dataForOthersData.postValue(Resource.PhoneNumberError("Phone Number missing"))
            return@launch
        }
        if (dataForOther.pesa.isEmpty()) {
            dataForOthersData.postValue(Resource.AmountError("Amount missing"))
            return@launch
        }
        val response = dataRepository.buyDataForOther(dataForOther)
        Log.d("DataViewModel", "retrofit response for others: $response")
        dataForOthersData.postValue(handleDataResponse(response))
    }

    private fun handleDataResponse(response: Response<DataPurchaseResponse>): Resource<DataPurchaseResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error("Error, failed to make request")
    }
}