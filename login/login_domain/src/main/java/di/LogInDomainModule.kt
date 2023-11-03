package di

import com.ondevop.login_domain.repository.AuthRepository
import com.ondevop.login_domain.use_case.SignInWithEmailAndPassword
import com.ondevop.login_domain.use_case.SignUpWithEmailAndPassword
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LogInDomainModule {

    @Provides
    @ViewModelScoped
    fun provideSignInUseCase(
        repository: AuthRepository
    ): SignInWithEmailAndPassword{
        return SignInWithEmailAndPassword(repository)
    }

    @Singleton
    @ViewModelScoped
    fun provideSignUpUseCase(
        repository: AuthRepository
    ): SignUpWithEmailAndPassword{
        return SignUpWithEmailAndPassword(repository)
    }
}