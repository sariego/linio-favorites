package dev.sariego.liniofavorites.core.network.interceptor

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.io.FileReader
import javax.inject.Inject

class MockClient @Inject constructor(
    private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url
        return when (url.encodedPath) {
            "api/favorites" -> {
                val asset = context.assets.openFd("favorites.json")
                val response = FileReader(asset.fileDescriptor).use {
                    it.readText()
                }
                Response.Builder()
                    .code(200)
                    .message(response)
                    .addHeader("content-type", "application/json")
                    .build();

            }
            else -> chain.proceed(chain.request())
        }
    }
}