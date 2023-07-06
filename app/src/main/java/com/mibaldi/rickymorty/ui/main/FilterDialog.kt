package com.mibaldi.rickymorty.ui.main

import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import com.mibaldi.rickymorty.R

fun MainActivity.openFilterWindow(viewModel: MainViewModel) {
    val builder = AlertDialog.Builder(this)
    val filterDialog = layoutInflater.inflate(R.layout.activity_filter,null)
    var dialog : AlertDialog? = null
    builder.setView(filterDialog)
    val spinnerStatus = filterDialog.findViewById<Spinner>(R.id.spinnerStatus)
    val spinnerGender = filterDialog.findViewById<Spinner>(R.id.spinnerGender)
    var selectedItemStatus = viewModel.currentStatusFilter.ifEmpty { "all" }
    var selectedItemGender = viewModel.currentGenderFilter.ifEmpty { "all" }
    prepareSpinner(viewModel.currentStatusFilter, spinnerStatus,R.array.spinner_status_items){
        selectedItemStatus = it
    }
    prepareSpinner(viewModel.currentGenderFilter, spinnerGender,R.array.spinner_gender_items){
        selectedItemGender = it
    }

    val btnApplyFilter = filterDialog.findViewById<Button>(R.id.btnApplyFilter)
    btnApplyFilter.setOnClickListener {
        viewModel.addFilters(selectedItemStatus,selectedItemGender)
        viewModel.getCharacters(1)
        dialog?.dismiss()
    }
    dialog = builder.create()
    dialog.show()

}
private fun MainActivity.prepareSpinner(
    currentFilter: String,
    spinner: Spinner,
    arrayInt: Int,
    onItemSelectedListener: (String)->Unit
) {
    var selectedItem = currentFilter.ifEmpty { "all" }
    val adapterStatus = ArrayAdapter.createFromResource(
        this,
        arrayInt,
        android.R.layout.simple_spinner_item
    )
    adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    spinner.adapter = adapterStatus
    val indexOfStatus =
        resources.getStringArray(arrayInt).indexOf(selectedItem)
    spinner.setSelection(indexOfStatus)
    spinner.onItemSelectedListener = object : OnItemSelectedListener {
        private var isFirstSelection = true // Variable para controlar la primera selección
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            if (isFirstSelection) {
                isFirstSelection = false
            } else {
                selectedItem = parent?.getItemAtPosition(position).toString()
                onItemSelectedListener(selectedItem)
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
        }
    }
}