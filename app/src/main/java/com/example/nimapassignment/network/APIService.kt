package com.example.nimapassignment.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

const val BASE_URL = "https://testffc.nimapinfotech.com/"
const val cacheSize = (5*1024*1024).toLong()


class RetrofitService @Inject constructor(@ApplicationContext context: Context){

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    private var isConnected = activeNetwork != null && activeNetwork.isConnected
    private val myCache = Cache(context.cacheDir, cacheSize)

    private var onlineInterceptor = Interceptor { chain ->
        val response = chain.proceed(chain.request())
        val maxAge = 60 // read from cache for 60 seconds even if there is internet connection
        response.newBuilder()
            .header("Cache-Control", "public, max-age=$maxAge")
            .removeHeader("Pragma")
            .build()
    }

    private var offlineInterceptor = Interceptor { chain ->
        var request: Request = chain.request()
        if (!isConnected) {
            val maxStale = 60 * 60 * 24 * 30 // Offline cache available for 30 days
            request = request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .removeHeader("Pragma")
                .build()
        }
        chain.proceed(request)
    }


    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(offlineInterceptor)
        .addNetworkInterceptor(onlineInterceptor)
        .cache(myCache)
        .build()
}
