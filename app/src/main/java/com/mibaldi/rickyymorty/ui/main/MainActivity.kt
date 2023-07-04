package com.mibaldi.rickyymorty.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.mibaldi.rickyymorty.databinding.ActivityMainBinding
import com.mibaldi.rickyymorty.ui.common.launchAndCollect
import dagger.hilt.android.AndroidEntryPoint
import com.mibaldi.rickyymorty.ui.common.errorToString

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding : ActivityMainBinding
    private val adapter = CharacterAdapter{
        Toast.makeText(this,"${it.name}", Toast.LENGTH_SHORT).show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycler.adapter = adapter

        launchAndCollect (viewModel.state){
            binding.loading = it.loading
            binding.characters = it.myCharacters
            binding.error = it.error?.let(::errorToString)
            Log.d("Characters",it.myCharacters.toString())
        }
        launchAndCollect(viewModel.info){
            Log.d("Page",it.toString())
        }

    }
}