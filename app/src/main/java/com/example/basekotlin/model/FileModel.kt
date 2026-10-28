package com.example.basekotlin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LanguageModel(
    var name: String,
    var code: String,
    var image: Int,
    var active: Boolean,
    var variants: MutableList<LanguageVariant>? = null,
) : Parcelable

@Parcelize
data class LanguageVariant(
    var name: String,
    var code: String,
    var active: Boolean = false,
) : Parcelable

@Parcelize
data class IntroModel(
    var title: Int = -1,
    var content: Int = -1,
    var image: String = "",
) : Parcelable
