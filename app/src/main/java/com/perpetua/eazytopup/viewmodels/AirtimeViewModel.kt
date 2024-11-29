package com.perpetua.eazytopup.viewmodels

import android.util.Log.d
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perpetua.eazytopup.models.AirtimeForOther
import com.perpetua.eazytopup.models.AirtimeForSelf
import com.perpetua.eazytopup.models.AirtimePurchaseResponse
import com.perpetua.eazytopup.repositories.AirtimeRepository
import com.perpetua.eazytopup.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class AirtimeViewModel(private val airtimeRepository: AirtimeRepository) : ViewModel() {

    val airtimeForSelfData: MutableLiveData<Resource<AirtimePurchaseResponse>> = MutableLiveData()
    init{
        d("AirtimeViewModel", "View model started")
    }
    fun buyAirtimeForSelf(airtimeForSelf: AirtimeForSelf) = viewModelScope.launch(Dispatchers.IO) {
        airtimeForSelfData.postValue(Resource.Loading())
        if(airtimeForSelf.number.isEmpty()){
            airtimeForSelfData.postValue(Resource.PhoneNumberError("Phone Number missing"))
        }
        if(airtimeForSelf.pesa.isEmpty()){
            airtimeForSelfData.postValue(Resource.AmountError("Amount missing"))
        }
        val response = airtimeRepository.buyAirtimeForSelf(airtimeForSelf)
        d("AirtimeViewModel", "retrofit response: $response")
        d("AirtimeViewModel", "response body: ${response}")
        airtimeForSelfData.postValue(handleAirtimeResponse(response))
       }

    fun buyAirtimeForOther(airtimeForOther: AirtimeForOther) = viewModelScope.launch(Dispatchers.IO) {
        airtimeForSelfData.postValue(Resource.Loading())
        if(airtimeForOther.number.isEmpty()){
            airtimeForSelfData.postValue(Resource.PhoneNumberError("Phone Number missing"))
        }
        if(airtimeForOther.msisdn.isEmpty()){
            airtimeForSelfData.postValue(Resource.PhoneNumberError("Phone Number missing"))
        }
        if(airtimeForOther.pesa.isEmpty()){
            airtimeForSelfData.postValue(Resource.AmountError("Amount missing"))
        }
        val response = airtimeRepository.buyAirtimeForOther(airtimeForOther)
        d("AirtimeViewModel", "retrofit response: $response")
        d("AirtimeViewModel", "response body: ${response}")
        airtimeForSelfData.postValue(handleAirtimeResponse(response))
    }


    private fun handleAirtimeResponse(response: Response<AirtimePurchaseResponse>) : Resource<AirtimePurchaseResponse> {
        if(response.isSuccessful){
            response.body()?.let{
                return Resource.Success(it)
            }
        }
        return Resource.Error("Error, failed to make request")
    }
}