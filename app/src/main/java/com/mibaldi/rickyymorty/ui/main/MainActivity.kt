package com.mibaldi.rickyymorty.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.mibaldi.rickyymorty.databinding.ActivityMainBinding
import com.mibaldi.rickyymorty.ui.common.launchAndCollect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        launchAndCollect (viewModel.state){
            Log.d("Characters",it.characters.toString())
        }
        launchAndCollect(viewModel.info){
            Log.d("Page",it.toString())
        }

    }
}