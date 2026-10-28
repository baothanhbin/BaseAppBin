package com.example.basekotlin.util

import android.content.Context
import com.example.basekotlin.R
import com.example.basekotlin.model.LanguageModel
import com.example.basekotlin.model.LanguageVariant
import java.util.Locale
import kotlin.code


object InsertListManager {

    fun getListLanguage(context: Context): MutableList<LanguageModel> {
        return mutableListOf(
            createLanguageModel(
                name = "English",
                code = "en",
                imageRes = R.drawable.ic_lang_en,
                variants = mutableListOf(
                    LanguageVariant(name = "English", code = "en-US"),
                    LanguageVariant(name = "English", code = "en-GB"),
                )
            ),
            createLanguageModel("German", "de", R.drawable.ic_lang_de),
            createLanguageModel("Spanish", "es", R.drawable.ic_lang_es),
            createLanguageModel("French", "fr", R.drawable.ic_lang_fr),
            createLanguageModel("Hindi", "hi", R.drawable.ic_lang_hi),
            createLanguageModel("Japanese", "ja", R.drawable.ic_lang_ja),
            createLanguageModel("Korean", "ko", R.drawable.ic_lang_ko),
            createLanguageModel("Portuguese", "pt", R.drawable.ic_lang_pt),
            createLanguageModel("Russian", "ru", R.drawable.ic_lang_ru),
        )
    }

    fun getLanguageName(context: Context, code: String): String {
        val normalizedCode = code.normalizeLanguageCode()
        return getListLanguage(context).firstNotNullOfOrNull { language ->
            when {
                language.code == normalizedCode -> language.name
                language.variants?.any { it.code.normalizeLanguageCode() == normalizedCode } == true -> language.name
                else -> null
            }
        }.orEmpty()
    }

    private fun createLanguageModel(
        name: String,
        code: String,
        imageRes: Int,
        variants: MutableList<LanguageVariant>? = null,
    ): LanguageModel {
        return LanguageModel(
            name = name,
            code = code,
            image = imageRes,
            active = false,
            variants = variants,
        )
    }

    private fun String.normalizeLanguageCode(): String {
        return when (lowercase()) {
            "en-us", "en-gb" -> "en"
            else -> lowercase()
        }
    }
}
