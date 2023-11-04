package di

import com.ondevop.login_domain.EmailValidator
import com.ondevop.login_domain.repository.AuthRepository
import com.ondevop.login_domain.use_case.SignInWithEmailAndPassword
import com.ondevop.login_domain.use_case.SignUpWithEmailAndPassword
import com.ondevop.login_domain.use_case.ValidateEmail
import com.ondevop.login_domain.use_case.ValidatePassword
import com.ondevop.login_domain.use_case.ValidateRepeatedPassword
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
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

    @Singleton
    @ViewModelScoped
     fun provideValidateEmailUseCase(
         emailValidator: EmailValidator
     ) :ValidateEmail{
         return ValidateEmail(emailValidator)
     }

    @Singleton
    @ViewModelScoped
    fun provideValidatePasswordUseCase() : ValidatePassword {
        return ValidatePassword()
    }

    @Singleton
    @ViewModelScoped
    fun provideValidateRepeatedPasswordUseCase() : ValidateRepeatedPassword {
        return ValidateRepeatedPassword()
    }

}