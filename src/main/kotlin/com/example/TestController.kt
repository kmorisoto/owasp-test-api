package com.example

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule

@Controller
class TestController {
    @Get
    @Secured(SecurityRule.IS_AUTHENTICATED)
    fun index() = HttpResponse.ok("OK")
}
