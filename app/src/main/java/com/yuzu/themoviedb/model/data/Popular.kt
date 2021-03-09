package com.yuzu.themoviedb.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Popular(
    @SerializedName("page")
    var page: Int? = null,
    @SerializedName("results")
    var popularData: List<PopularData>? = null,
    @SerializedName("total_pages")
    var totalPages: Int? = null,
    @SerializedName("total_results")
    var totalResults: Int? = null
): Parcelable