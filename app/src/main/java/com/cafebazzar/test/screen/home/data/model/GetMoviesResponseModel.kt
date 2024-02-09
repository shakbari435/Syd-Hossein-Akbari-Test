package com.cafebazzar.test.screen.home.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class GetMoviesResponseModel(
    @SerializedName("dates") var dates: Dates? = Dates(),
    @SerializedName("page") var page: Int? = null,
    @SerializedName("results") var movies: ArrayList<Movie> = arrayListOf(),
    @SerializedName("total_pages") var totalPages: Int? = null,
    @SerializedName("total_results") var totalResults: Int? = null
) {
    @Entity(tableName = "movie_table")
    data class Movie(
        @PrimaryKey(autoGenerate = true)
        val primaryKey:Int? = null,
        @SerializedName("id") var id: Int? = null,
        @SerializedName("adult") var adult: Boolean? = null,
        @SerializedName("backdrop_path") var backdropPath: String? = null,
        @SerializedName("original_language") var originalLanguage: String? = null,
        @SerializedName("original_title") var originalTitle: String? = null,
        @SerializedName("overview") var overview: String? = null,
        @SerializedName("popularity") var popularity: Double? = null,
        @SerializedName("poster_path") var posterPath: String? = null,
        @SerializedName("release_date") var releaseDate: String? = null,
        @SerializedName("title") var title: String? = null,
        @SerializedName("video") var video: Boolean? = null,
        @SerializedName("vote_average") var voteAverage: Double? = null,
        @SerializedName("vote_count") var voteCount: Int? = null
    )
    data class Dates(
        @SerializedName("maximum") var maximum: String? = null,
        @SerializedName("minimum") var minimum: String? = null
    )
}
