package com.mibaldi.rickymorty.ui.main

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import com.mibaldi.rickymorty.R
import com.mibaldi.rickymorty.databinding.ActivityMainBinding
import com.mibaldi.rickymorty.ui.common.errorToString
import com.mibaldi.rickymorty.ui.common.goToDetail
import com.mibaldi.rickymorty.ui.common.launchAndCollect
import dagger.hilt.android.AndroidEntryPoint


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
        }
        launchAndCollect(viewModel.pages) {
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
            openFilterWindow(viewModel)
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
}