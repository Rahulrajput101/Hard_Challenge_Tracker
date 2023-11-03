package di

import android.app.Application
import com.ondevop.core.data.prefrences.DefaultPreferences
import com.ondevop.core.data.prefrences.dataStore
import com.ondevop.core.domain.prefernces.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providePreference(
        app: Application
    ): Preferences {
        return DefaultPreferences(app.dataStore)
    }
}