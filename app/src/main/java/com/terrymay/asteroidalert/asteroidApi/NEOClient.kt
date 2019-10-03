package com.terrymay.asteroidalert.asteroidApi

import android.util.Log
import com.terrymay.asteroidalert.BuildConfig
import com.terrymay.asteroidalert.models.browse.AsteroidBrowseResult
import com.terrymay.asteroidalert.models.feed.AsteroidFeedModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.LocalDate

class NEOClient() {

    private val httpClient = OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            } }.build()

    private val neoApi = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/neo/rest/v1/")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(httpClient)
        .build().create(NeoApi::class.java)

    fun getFeedList(startDate: LocalDate, endDate: LocalDate): Observable<AsteroidFeedModel> {
        Log.i("OUTPUT", startDate.toString())
        return neoApi.getFeed(startDate.toString(), endDate.toString(), BuildConfig.NASA_KEY)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun getBrowseList(): Observable<AsteroidBrowseResult> {
        return neoApi.getBrowseList(BuildConfig.NASA_KEY)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}