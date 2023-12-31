package com.mibaldi.rickymorty.ui.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.mibaldi.rickymorty.App
import com.mibaldi.rickymorty.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import com.mibaldi.rickymorty.domain.Error
import com.mibaldi.rickymorty.ui.detail.DetailActivity

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = true): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

inline fun <T : Any> basicDiffUtil(
    crossinline areItemsTheSame: (T, T) -> Boolean = { old, new -> old == new },
    crossinline areContentsTheSame: (T, T) -> Boolean = { old, new -> old == new }
) = object : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        areItemsTheSame(oldItem, newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        areContentsTheSame(oldItem, newItem)
}
fun <T> LifecycleOwner.launchAndCollect(
    flow: Flow<T>,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    body: (T) -> Unit
) {
    lifecycleScope.launch {
        this@launchAndCollect.repeatOnLifecycle(state) {
            flow.collect(body)
        }
    }
}
fun Context.errorToString(error: Error) = when (error) {
    Error.Connectivity -> getString(R.string.connectivity_error)
    is Error.Server -> getString(R.string.server_error) + error.code
    is Error.Unknown -> getString(R.string.unknown_error) + error.message
}
val Context.app: App get() = applicationContext as App
fun ImageView.loadUrl(url: String) {
    Glide.with(context).load(url).into(this)
}

fun Activity.goToDetail(characterID: Int) {
    val intent = Intent(this, DetailActivity::class.java)
    intent.putExtra("characterID", characterID)
    startActivity(intent)
}