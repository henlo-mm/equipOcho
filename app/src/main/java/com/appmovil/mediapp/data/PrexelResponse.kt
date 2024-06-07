package com.appmovil.mediapp.data

data class PexelsResponse(
    val total_results: Int,
    val photos: List<Photo>
)

data class Photo(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val src: Src
)

data class Src(
    val original: String,
    val medium: String
)