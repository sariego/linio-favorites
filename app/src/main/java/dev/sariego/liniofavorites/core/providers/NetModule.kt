package dev.sariego.liniofavorites.core.providers

import android.content.Context
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dev.sariego.liniofavorites.core.network.interceptors.MockClient
import dev.sariego.liniofavorites.core.providers.qualifier.ServerUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(ActivityRetainedComponent::class)
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
    @ActivityRetainedScoped
    fun provideMoshi(): Moshi = Moshi.Builder()
        .build()

    @Provides
    @ActivityRetainedScoped
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val mockClient = MockClient(context)

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(mockClient)
            .build()
    }
}
