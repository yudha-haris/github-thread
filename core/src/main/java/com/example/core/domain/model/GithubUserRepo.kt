package com.example.core.domain.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class GithubUserRepo(
    val language: String,
    val id: Int,
    val name: String,
    val description: String,
) : Parcelable