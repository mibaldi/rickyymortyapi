package com.mibaldi.rickyymorty.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.mibaldi.rickyymorty.R
import com.mibaldi.rickyymorty.databinding.ActivityDetailBinding
import com.mibaldi.rickyymorty.domain.MyCharacter
import com.mibaldi.rickyymorty.ui.common.launchAndCollect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val viewModel: DetailViewModel by viewModels()
    lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.characterDetailToolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }
        val characterID = intent.extras?.getInt("characterID") ?: 0
        viewModel.getCharacter(characterID)
        launchAndCollect(viewModel.state){
            if (it.myCharacter != null){
                binding.character = it.myCharacter
            }
        }

    }
}