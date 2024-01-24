package com.example.contactify.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contactify.data.UsersModel
import com.example.contactify.retrofit.RetroInstance
import com.example.contactify.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel: ViewModel() {

    var liveDataList: MutableLiveData<UsersModel> = MutableLiveData()

    fun getLiveDataObserver(): MutableLiveData<UsersModel> {
        return liveDataList
    }

    fun makeAPICall() {
        val retroInstance = RetroInstance.getRetroInstance()
        val retroService  = retroInstance.create(RetroServiceInterface::class.java)
        val call  = retroService.getUsersList()
        call.enqueue(object : Callback<UsersModel> {
            override fun onFailure(call: Call<UsersModel>, t: Throwable) {
                liveDataList.postValue(null)
            }

            override fun onResponse(
                call: Call<UsersModel>,
                response: Response<UsersModel>
            ) {
                liveDataList.postValue(response.body())
            }
        })


    }
}