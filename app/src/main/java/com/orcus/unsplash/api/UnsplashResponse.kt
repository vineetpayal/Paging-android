package com.orcus.unsplash.api

import com.orcus.unsplash.data.UnsplashPhoto

data class UnsplashResponse(
    val results : List<UnsplashPhoto>
)