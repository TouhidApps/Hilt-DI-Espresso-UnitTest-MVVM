package com.touhidapps.currency.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.touhidapps.currency.db.AppDatabase
import com.touhidapps.currency.db.CurrencyDao
import com.touhidapps.currency.db.DbConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, DbConstants.DATABASE_NAME)
            .build()
            .also {
                Log.d("<ROOM_TAG>", it?.openHelper?.writableDatabase?.path ?: "")
            }

    @Provides
    @Singleton
    fun provideCurrencyDao(db: AppDatabase): CurrencyDao = db.contactDao()

}