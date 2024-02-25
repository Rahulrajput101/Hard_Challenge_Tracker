package di

import android.app.Application
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.ondevop.a75hard.EmailValidatorImp
import com.ondevop.core_data.prefrences.DefaultPreferences
import com.ondevop.core_data.prefrences.dataStore
import com.ondevop.core_domain.prefernces.Preferences
import com.ondevop.login_domain.EmailValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Named("webClientId")
    fun provideWebClientId(): String {
        return "621978207845-6maius9oe77h3ms2sruoth6u7lcuqivi.apps.googleusercontent.com"
    }
    @Provides
    @Singleton
    fun providePreference(
        app: Application
    ): Preferences {
        return DefaultPreferences(app.dataStore)
    }

    @Provides
    @Singleton
    fun provideEmailValidator() : EmailValidator{
       return EmailValidatorImp()
    }

    @Singleton
    @Provides
    fun provideGoogleSignClient(
        app: Application,
        @Named("webClientId") webClientId: String
    ) : GoogleSignInClient {
        return GoogleSignIn.getClient(
            app,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(webClientId)
                .requestEmail()
                .build()
        )
    }

    @Provides
    @Singleton
    fun provideAppUpdateManager(application: Application): AppUpdateManager {
        return AppUpdateManagerFactory.create(application)
    }
}