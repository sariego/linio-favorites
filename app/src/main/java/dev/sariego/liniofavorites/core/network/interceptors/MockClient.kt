package dev.sariego.liniofavorites.core.network.interceptors

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject

class MockClient @Inject constructor(
    private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url
        return when (url.encodedPath) {
            "/api/favorites" -> {
                val response = context.assets
                    .open("favorites.json")
                    .bufferedReader()
                    .use { it.readText() }
                Response.Builder()
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_1)
                    .code(200)
                    .message("OK")
                    .body(response.toResponseBody())
                    .addHeader("content-type", "application/json")
                    .build()
            }
            else -> chain.proceed(chain.request())
        }
    }
}
