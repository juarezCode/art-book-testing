package com.juarezcode.artbooktesting.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.juarezcode.artbooktesting.R
import com.juarezcode.artbooktesting.api.RetrofitApi
import com.juarezcode.artbooktesting.repo.ArtRepository
import com.juarezcode.artbooktesting.repo.ArtRepositoryInterface
import com.juarezcode.artbooktesting.roomdb.ArtDao
import com.juarezcode.artbooktesting.roomdb.ArtDatabase
import com.juarezcode.artbooktesting.util.Util
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): ArtDatabase {
        return Room.databaseBuilder(
            context,
            ArtDatabase::class.java,
            "art-bok-database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideDao(database: ArtDatabase): ArtDao {
        return database.artDao()
    }

    @Singleton
    @Provides
    fun provideRetrofitAPI(): RetrofitApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Util.BASE_URL)
            .build()
            .create(RetrofitApi::class.java)
    }

    @Singleton
    @Provides
    fun provideArtRepository(dao: ArtDao, api: RetrofitApi): ArtRepositoryInterface {
        return ArtRepository(dao, api)
    }


    @Singleton
    @Provides
    fun provideGlide(@ApplicationContext context: Context): RequestManager {
        return Glide.with(context).setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
        )
    }
}