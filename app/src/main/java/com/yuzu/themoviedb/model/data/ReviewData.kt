package com.yuzu.themoviedb.model.data


import com.google.gson.annotations.SerializedName

data class ReviewData(
    @SerializedName("author")
    var author: String? = null,
    @SerializedName("author_details")
    var authorDetails: AuthorDetails? = null,
    @SerializedName("content")
    var content: String? = null,
    @SerializedName("created_at")
    var createdAt: String? = null,
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("updated_at")
    var updatedAt: String? = null,
    @SerializedName("url")
    var url: String? = null
)