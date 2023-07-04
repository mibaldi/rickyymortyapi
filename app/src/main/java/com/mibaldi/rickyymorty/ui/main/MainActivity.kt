package com.mibaldi.rickyymorty.ui.main

import android.os.Bundle
import android.util.Log
import android.view.Menu
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        val search = menu?.findItem(R.id.action_search)
        val searchView = search?.actionView as SearchView
        val queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.getCharaters(1, mapOf(Pair("name",query)))
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()){
                    viewModel.getCharaters(1, emptyMap())
                }
                return true
            }
        }
        searchView.setOnQueryTextListener(queryTextListener)
        return true
    }
}