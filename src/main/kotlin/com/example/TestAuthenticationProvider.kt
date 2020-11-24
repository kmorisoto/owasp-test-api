package com.example

import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.AuthenticationException
import io.micronaut.security.authentication.AuthenticationFailed
import io.micronaut.security.authentication.AuthenticationProvider
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.authentication.UserDetails
import io.reactivex.Maybe
import io.reactivex.MaybeEmitter
import org.reactivestreams.Publisher
import java.util.ArrayList
import javax.inject.Singleton
import kotlin.random.Random


@Singleton
class TestAuthenticationProvider: AuthenticationProvider {
    override fun authenticate(
        httpRequest: HttpRequest<*>?,
        authenticationRequest: AuthenticationRequest<*, *>?
    ): Publisher<AuthenticationResponse>? {
        return Maybe.create { emitter: MaybeEmitter<AuthenticationResponse> ->
            if (authenticationRequest!!.identity == "123") {
                emitter.onSuccess(UserDetails("hogehoge", listOf()))
            } else {
                emitter.onError(AuthenticationException(AuthenticationFailed()))
            }
        }.toFlowable()
    }
}
