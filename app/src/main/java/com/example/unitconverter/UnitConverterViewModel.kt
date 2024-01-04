package com.example.unitconverter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.unitconverter.data.UnitConverterModel
import com.example.unitconverter.data.UnitConverterRepository
import kotlin.math.roundToInt

class UnitConverterViewModel(
    private val repository: UnitConverterRepository
) : ViewModel() {

    val model: LiveData<UnitConverterModel>
        get() = repository.model

    fun updateModel(updatedModel: UnitConverterModel) {
        Log.d("Update Model fun","In")
        repository.updateModel(updatedModel)
        converterUnits()
    }
    private fun converterUnits() {
        val inputValueDouble =  model.value?.inputValue?.toDoubleOrNull() ?: 0.0
        val result = (inputValueDouble * model.value!!.iConversionFactor * 100.0 / model.value!!.oConversionFactor).roundToInt() / 100.0
        val updatedModel = model.value?.copy(outputValue = result.toString())
        updatedModel?.let { repository.updateModel(it) }
//        repository.updateModel(updatedModel!!)
    }

    fun toggleInputDropdown() {
        val updatedModel = model.value?.copy(iExpanded = !model.value!!.iExpanded)
        repository.updateModel(updatedModel!!)
    }

    fun toggleOutputDropdown(){
        val updatedModel = model.value?.copy(oExpanded = !model.value!!.oExpanded)
        repository.updateModel(updatedModel!!)
    }

    fun updateInputUnit(
        inputUnit: String,
        conversionFactor: Double
    ) {
        Log.d("Update Input Unit","in")
        val updatedModel = model.value?.copy(inputUnit = inputUnit, iConversionFactor = conversionFactor)
        updateModel(updatedModel!!)
    }

    fun updateOutputUnit(
        outputUnit: String,
        conversionFactor: Double
    ) {
        val updatedModel = model.value?.copy(outputUnit = outputUnit, oConversionFactor = conversionFactor)
        updateModel(updatedModel!!)
    }
}