package com.mibaldi.rickyymorty.ui.main

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import com.mibaldi.rickyymorty.R
import com.mibaldi.rickyymorty.databinding.ActivityMainBinding
import com.mibaldi.rickyymorty.ui.common.errorToString
import com.mibaldi.rickyymorty.ui.common.goToDetail
import com.mibaldi.rickyymorty.ui.common.launchAndCollect
import dagger.hilt.android.AndroidEntryPoint
import java.util.Arrays


@AndroidEntryPoint
class MainActivity : AppCompatActivity(){
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private val adapter = CharacterAdapter {
        this.goToDetail(it.id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.adapter = adapter
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        launchAndCollect(viewModel.state) {
            binding.loading = it.loading
            binding.characters = it.myCharacters
            binding.error = it.error?.let(::errorToString)
            Log.d("Characters", it.myCharacters.toString())
        }
        launchAndCollect(viewModel.pages) {
            Log.d("Pages", it.toString())
            binding.prev = it.prev
            binding.next = it.next
            binding.page = "PAGE: ${it.page}"
        }

        binding.btnNext.setOnClickListener {
            viewModel.loadNextItems()
        }
        binding.btnPrev.setOnClickListener {
            viewModel.loadPrevItems()
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.action_filter) {
            openFilterWindow()
            true
        } else super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        val search = menu?.findItem(R.id.action_search)
        val searchView = search?.actionView as SearchView
        val queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.getCharacters(1, mapOf(Pair("name",query)))
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()){
                    viewModel.getCharacters(1, emptyMap())
                }
                return true
            }
        }
        searchView.setOnQueryTextListener(queryTextListener)
        return true
    }
    private fun openFilterWindow() {
        val filterDialog = Dialog(this@MainActivity)
        filterDialog.setContentView(R.layout.activity_filter)
        var selectedItemStatus = viewModel.currentStatusFilter.ifEmpty { "all" }
        var selectedItemGender = viewModel.currentGenderFilter.ifEmpty { "all" }

        val spinnerStatus = filterDialog.findViewById<Spinner>(R.id.spinnerStatus)

        val adapterStatus = ArrayAdapter.createFromResource(
            this,
            R.array.spinner_status_items,
            android.R.layout.simple_spinner_item
        )
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerStatus.adapter = adapterStatus
        val indexOfStatus =
            resources.getStringArray(R.array.spinner_status_items).indexOf(selectedItemStatus)
        spinnerStatus.setSelection(indexOfStatus)
        spinnerStatus.onItemSelectedListener = object : OnItemSelectedListener{
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
                    selectedItemStatus = parent?.getItemAtPosition(position).toString()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        val spinnerGender = filterDialog.findViewById<Spinner>(R.id.spinnerGender)
        val adapterGender = ArrayAdapter.createFromResource(
            this,
            R.array.spinner_gender_items,
            android.R.layout.simple_spinner_item
        )
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender.adapter = adapterGender
        val indexOfGender =
            resources.getStringArray(R.array.spinner_gender_items).indexOf(selectedItemGender)
        spinnerGender.setSelection(indexOfGender)
        spinnerGender.onItemSelectedListener = object : OnItemSelectedListener{
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
                    selectedItemGender = parent?.getItemAtPosition(position).toString()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        val btnApplyFilter = filterDialog.findViewById<Button>(R.id.btnApplyFilter)
        btnApplyFilter.setOnClickListener {

            viewModel.addFilters(selectedItemStatus,selectedItemGender)
            viewModel.getCharacters(1)
            filterDialog.dismiss()
        }
        filterDialog.show()

    }

}