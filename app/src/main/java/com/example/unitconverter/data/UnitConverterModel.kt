package com.example.unitconverter.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

data class UnitConverterModel(
    var inputValue: String,
    var outputValue: String,
    var inputUnit: String = "Meters",
    var outputUnit: String = "Meters",
    var iExpanded: Boolean = false,
    var oExpanded: Boolean = false,
    val iConversionFactor: Double = 1.0,
    val oConversionFactor: Double = 1.0
)

class UnitConverterRepository {

    private val _model = MutableLiveData(UnitConverterModel(
        inputValue = "",
        outputValue = "",
        inputUnit = "Meters",
        outputUnit = "Meters",
        iExpanded = false,
        oExpanded = false,
        iConversionFactor = 1.0,
        oConversionFactor = 1.0
    ))

    val model: LiveData<UnitConverterModel>
        get() = _model
    fun updateModel(updateModel: UnitConverterModel) {
        Log.d("In Rep Update Model", "In")
        _model.value = updateModel
    }

}