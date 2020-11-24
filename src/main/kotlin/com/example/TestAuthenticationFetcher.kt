package com.example

import io.micronaut.core.async.publisher.Publishers
import io.micronaut.http.HttpHeaderValues
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.authentication.AuthenticationUserDetailsAdapter
import io.micronaut.security.authentication.Authenticator
import io.micronaut.security.authentication.BasicAuthAuthenticationFetcher
import io.micronaut.security.authentication.DefaultAuthentication
import io.micronaut.security.filters.AuthenticationFetcher
import io.reactivex.Flowable
import org.reactivestreams.Publisher
import java.util.Optional
import javax.inject.Singleton


@Singleton
class TestAuthenticationFetcher(
    private val authenticator: Authenticator
) : AuthenticationFetcher {

    companion object {
        private const val PREFIX = HttpHeaderValues.AUTHORIZATION_PREFIX_BEARER + " "
    }

    override fun fetchAuthentication(request: HttpRequest<*>): Publisher<Authentication> {
        val credentials = request.headers.authorization.flatMap { this.parseCredential(it) }
        if (credentials.isPresent) {
            val authenticationResponse:Flowable<AuthenticationResponse> = Flowable.fromPublisher(
                authenticator.authenticate(
                    request,
                    credentials.get()
                )
            )
            return authenticationResponse.switchMap { response: AuthenticationResponse ->
                if (response.isAuthenticated) {
                    val userDetails = response.userDetails.get()
                    return@switchMap Flowable.just(
                        AuthenticationUserDetailsAdapter(
                            userDetails,
                            "",
                            ""
                        )
                    )
                } else {
                    return@switchMap Flowable.empty<Authentication>()
                }
            }
        } else {
            return Publishers.empty()
        }
    }

    private fun parseCredential(authorization: String): Optional<TestCredentials> = Optional.of(authorization)
        .filter { it.startsWith(PREFIX) }
        .map { it.substring(PREFIX.length) }
        .flatMap { Optional.of(TestCredentials(it)) }
}
