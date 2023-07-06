package com.mibaldi.rickymorty.ui.detail

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.net.toUri
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.mibaldi.rickymorty.R
import com.mibaldi.rickymorty.domain.MyCharacter

class CharacterDetailInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    fun setCharacter(character: MyCharacter) = character.apply {
        text = buildSpannedString {

            bold { append(context.getString(R.string.status)) }
            appendLine(status)

            bold { append(context.getString(R.string.species)) }
            appendLine(species)

            bold { append(context.getString(R.string.gender)) }
            appendLine(gender)

            bold { append(context.getString(R.string.type)) }
            appendLine(type)

            bold { append(context.getString(R.string.origin)) }
            appendLine(origin.name)

            bold { append(context.getString(R.string.location)) }
            appendLine(location.name)

            bold { append(context.getString(R.string.episode)) }
            append(episode.map {
                val segments = it.toUri().path?.split("/")
                segments?.last()
            }.toString())
        }
    }
}