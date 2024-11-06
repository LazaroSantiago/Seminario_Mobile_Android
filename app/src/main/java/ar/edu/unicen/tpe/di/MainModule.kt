package ar.edu.unicen.tpe.di

import ar.edu.unicen.tpe.data.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainModule {
    private val baseUrl = "https://api.themoviedb.org/3/"
    private val token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhNGQ4ZTZkMmU3NGQzMjRhODJiZjQ2MzZjMGIxZGEwMiIsIm5iZiI6MTczMDgxNTE0MC4xMzg2MzkyLCJzdWIiOiI2NzI4MDM3YzU5MTgxMzdjZmMzOWE4N2MiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.OgEszoRE13Pt_SaLW7Wk3I8YuGUym7i9g8yzlre95zU"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideTokenKey(): String {
        return token
    }

    @Provides
    @Singleton
    fun provideMovieApi(
        retrofit: Retrofit
    ) : MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

}