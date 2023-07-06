package com.mibaldi.rickymorty.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.mibaldi.rickymorty.databinding.ActivityDetailBinding
import com.mibaldi.rickymorty.ui.common.errorToString
import com.mibaldi.rickymorty.ui.common.launchAndCollect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.characterDetailToolbar)
        binding.characterDetailToolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }
        val characterID = intent.extras?.getInt("characterID") ?: 0
        viewModel.getCharacter(characterID)
        launchAndCollect(viewModel.state){
            binding.loading = it.loading
            binding.error = it.error?.let(::errorToString)
            if (it.myCharacter != null){
                binding.character = it.myCharacter
            }
        }

    }
}