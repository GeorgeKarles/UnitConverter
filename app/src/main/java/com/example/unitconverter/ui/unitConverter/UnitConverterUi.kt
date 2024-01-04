package com.example.unitconverter.ui.unitConverter

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.unitconverter.UnitConverterViewModel
import com.example.unitconverter.data.UnitConverterModel


@Composable
fun UnitConverter(
    viewModel: UnitConverterViewModel
) {
    val model by viewModel.model.observeAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Unit Converter", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        TextField(viewModel = viewModel, model = model!!)
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            DropdownMenu(
                viewModel = viewModel,
                model = model!!,
                type = "input"
            )
            Spacer(modifier = Modifier.width(24.dp))
            DropdownMenu(
                viewModel = viewModel,
                model = model!!,
                type = "output"
            )
        }
        Result(model = model!!)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextField(
    viewModel: UnitConverterViewModel,
    model: UnitConverterModel
) {
    OutlinedTextField(
        value = viewModel.model.value?.inputValue ?: "",
        onValueChange = {
            viewModel.updateModel(model.copy(inputValue = it))
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        modifier = Modifier.padding(8.dp),
        label = { Text("Enter Value") }
    )
}

@Composable
fun Result(
    model: UnitConverterModel
) {
    Text(
        text = "Result: ${model.outputValue} ${model.outputUnit}",
        modifier = Modifier.padding(16.dp),
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
fun DropdownMenu(
    viewModel: UnitConverterViewModel,
    model: UnitConverterModel,
    type: String
) {
    val expanded : Boolean = if (type == "input") {
        model.iExpanded
    } else {
        model.oExpanded
    }


    Box (
        contentAlignment = Alignment.Center
    ) {
        Button(
            modifier = Modifier.width(140.dp),
            onClick = {
            if (type == "input")
                viewModel.toggleInputDropdown()
            else
                viewModel.toggleOutputDropdown()
        }) {
            if (type == "input") {
                Text(text = model.inputUnit)
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown menu"
                )
            } else {
                Text(text = model.outputUnit)
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown menu"
                )
            }

        }
        DropdownMenu(
            expanded =  expanded,
            modifier = Modifier.align(Alignment.Center),
            onDismissRequest = {
                if (type == "input") {
                    viewModel.toggleInputDropdown()
                }else {
                    viewModel.toggleOutputDropdown()
                }
            }
        ) {
            DropDownItem(
                text = "Centimeters",
                onClick = {
                    if (type == "input") {
                        Log.d("Input DropDownMenu", "In Centimeters")
                        viewModel.updateInputUnit("Centimeters", 0.01)
                        viewModel.toggleInputDropdown()
                    }else {
                        viewModel.updateOutputUnit("Centimeters", 0.01)
                        viewModel.toggleOutputDropdown()
                    }
                }
            )
            DropDownItem(
                text = "Meters",
                onClick = {
                    if (type == "input") {
                        viewModel.updateInputUnit("Meters", 1.0)
                        viewModel.toggleInputDropdown()
                    }else {
                        viewModel.updateOutputUnit("Meters", 1.0)
                        viewModel.toggleOutputDropdown()
                    }
                }
            )
            DropDownItem(
                text = "Feet",
                onClick = {
                    if (type == "input") {
                        viewModel.updateInputUnit("Feet", 0.3048)
                        viewModel.toggleInputDropdown()
                    } else {
                        viewModel.updateOutputUnit("Feet", 0.3048)
                        viewModel.toggleOutputDropdown()
                    }
                }
            )
            DropDownItem(
                text = "Millimeters",
                onClick = {
                    if (type == "input") {
                        viewModel.updateInputUnit("Millimeters", 0.001)
                        viewModel.toggleInputDropdown()
                    } else {
                        viewModel.updateOutputUnit("Millimeters", 0.001)
                        viewModel.toggleOutputDropdown()
                    }
                }
            )
        }
    }
}

@Composable
fun DropDownItem(
    text: String,
    onClick: () -> Unit
) {
    DropdownMenuItem(
        text = { Text(text = text) },
        onClick = onClick,
    )
}
