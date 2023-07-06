package com.mibaldi.rickymorty.ui.detail

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.net.toUri
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.mibaldi.rickymorty.domain.MyCharacter

class CharacterDetailInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    fun setCharacter(character: MyCharacter) = character.apply {
        text = buildSpannedString {

            bold { append("Status: ") }
            appendLine(status)

            bold { append("Species: ") }
            appendLine(species)

            bold { append("Gender: ") }
            appendLine(gender)

            bold { append("Type: ") }
            appendLine(type)

            bold { append("Origin: ") }
            appendLine(origin.name)

            bold { append("Location: ") }
            appendLine(location.name)

            bold { append("Episode: ") }
            append(episode.map {
                val segments = it.toUri().path?.split("/")
                segments?.last()
            }.toString())
        }
    }
}