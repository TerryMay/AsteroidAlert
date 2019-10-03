package com.terrymay.asteroidalert.asteroidApi

import com.terrymay.asteroidalert.models.browse.AsteroidBrowseResult
import com.terrymay.asteroidalert.models.feed.AsteroidFeedModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NeoApi {

 @GET("feed")
 fun getFeed(@Query("start_date") startDate: String,
             @Query("end_date") endDate: String,
             @Query("api_key") key: String): Observable<AsteroidFeedModel>

 @GET("browse")
 fun getBrowseList(@Query("api_key") key: String): Observable<AsteroidBrowseResult>
}