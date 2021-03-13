package com.yuzu.themoviedb.model.data


import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("page")
    var page: Int? = null,
    @SerializedName("results")
    var reviewData: List<ReviewData>? = null,
    @SerializedName("total_pages")
    var totalPages: Int? = null,
    @SerializedName("total_results")
    var totalResults: Int? = null
)