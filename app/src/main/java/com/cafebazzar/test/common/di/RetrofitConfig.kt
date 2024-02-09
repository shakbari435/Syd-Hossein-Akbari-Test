package com.cafebazzar.test.common.di

data class RetrofitConfig(
        var url: String = "https://api.themoviedb.org",
        var timeOut: Long = 30L,
        var token: String =  "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkNmFhYjc4ODM0NTk4ODhlZGEzNjkxYmFmYzhkZTg5NCIsInN1YiI6IjY1YzRiNDFjOTQ1MWU3MDE4NGJkNDhlYiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.0GYUH1EB9oG98su3I-ws_xw3r8sCDUw4QMWIZRFV3C4"
)