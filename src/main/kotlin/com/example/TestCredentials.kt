package com.example

import io.micronaut.core.annotation.Introspected
import io.micronaut.security.authentication.AuthenticationRequest
import java.io.Serializable

@Introspected
class TestCredentials(
    val token: String
) : Serializable, AuthenticationRequest<String, String> {
    override fun getIdentity(): String = token

    override fun getSecret(): String {
        TODO("Not yet implemented")
    }
}
