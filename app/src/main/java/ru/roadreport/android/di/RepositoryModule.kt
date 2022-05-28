package ru.roadreport.android.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.roadreport.android.data.local.dao.DraftDAO
import ru.roadreport.android.data.repository.DraftRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideDraftRepository(
        draftDAO: DraftDAO,
    ) = DraftRepository(
        draftDAO
    )
}