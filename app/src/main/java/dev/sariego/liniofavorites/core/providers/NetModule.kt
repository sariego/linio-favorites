package dev.sariego.liniofavorites.core.providers

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.sariego.liniofavorites.core.network.interceptor.MockClient
import dev.sariego.liniofavorites.core.providers.qualifier.ServerUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetModule {

    @Provides
    fun provideRetrofit(
        @ServerUrl serverUrl: String,
        okHttpClient: OkHttpClient,
        moshi: Moshi,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(serverUrl)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
    ): OkHttpClient {
        val logging =  HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val mockClient = MockClient(context)

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(mockClient)
            .build()
    }
}
