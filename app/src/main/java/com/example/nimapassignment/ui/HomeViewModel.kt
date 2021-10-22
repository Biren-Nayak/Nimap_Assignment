package com.example.nimapassignment.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nimapassignment.models.Collection
import com.example.nimapassignment.network.API
import com.example.nimapassignment.ui.LoadingStates.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject

enum class  LoadingStates{LOADING, SUCCESS, ERROR}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val api: API
): ViewModel() {

    private val _loadingStates = MutableLiveData<LoadingStates>()
    val loadingStates : LiveData<LoadingStates>
        get() = _loadingStates

    private val _itemsList = MutableLiveData<List<Collection>>()
    val items: LiveData<List<Collection>>
        get() =_itemsList


    private fun getData() = viewModelScope.launch {
        _loadingStates.value = LOADING
        try {
            _itemsList.value = api.getRecords().data.Records.mapIndexed { index, record ->
                Collection(
                    record.title,
                    record.shortDescription,
                    record.collectedValue.toString(),
                    record.totalValue.toString(),
                    SimpleDateFormat("dd/MM/yyyy").parse(record.startDate)!!,
                    SimpleDateFormat("dd/MM/yyyy").parse(record.endDate)!!,
                    record.mainImageURL
                )
            }

            _loadingStates.value = SUCCESS
        }catch (e: Exception){
            _loadingStates.value = ERROR
            Log.e("ViewModel",e.message.toString())
            }
        }

    init{
        getData()
    }


}