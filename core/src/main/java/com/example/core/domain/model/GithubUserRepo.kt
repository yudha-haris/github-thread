package com.example.core.domain.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class GithubUserRepo(
    val language: String,
    val id: Int,
    val fullName: String,
    val name: String,
    val description: String,
    val createdAt: String,
    val updatedAt: String,
) : Parcelable