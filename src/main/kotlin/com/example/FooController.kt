package com.example

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule

@Controller("/foo")
class FooController {

    @Get
    @Secured(SecurityRule.IS_AUTHENTICATED)
    fun get(): Foo = Foo("nekotaro", 1)

    @Post
    @Secured(SecurityRule.IS_AUTHENTICATED)
    fun post(@Body foo: Foo): Foo = foo
}

data class Foo(
    val name: String,
    val age: Int
)
