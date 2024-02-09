package com.cafebazzar.test.common.base.mvi.exception

sealed class DomainErrorHolder(
    val errorList: List<ErrorResponseMessage>
) : Exception() {

    // 9090 -
    data class ForceLogout(
        val msg: String = ""
    ) : DomainErrorHolder(
        listOf(
            ErrorResponseMessage(
                message = msg
            )
        )
    )

    // null request body
    data class EmptyRequest(
        val msg: String = "محتوی مورد نظر موجود نیست."
    ) : DomainErrorHolder(
        listOf(
            ErrorResponseMessage(
                message = msg
            )
        )
    )

    // null response body
    data class EmptyResponse(
        val msg: String = "محتوی مورد نظر موجود نیست."
    ) : DomainErrorHolder(
        listOf(
            ErrorResponseMessage(
                message = msg
            )
        )
    )

    // parse exception

    data class ParseError(
        val msg: String = "خطا در برقراری ارتباط."
    ) : DomainErrorHolder(
        listOf(
            ErrorResponseMessage(
                message = msg
            )
        )
    )


    data class ShowMessage(
        val msg: String = "خطا در برقراری ارتباط."
    ) : DomainErrorHolder(
        listOf(
            ErrorResponseMessage(
                message = msg
            )
        )
    )

    // unknown status code
    data class ShowErrorMessages(
        val errors: List<ErrorResponseMessage>
    ) : DomainErrorHolder(errors)


    // 400
    data class BadRequest(
        val errors: List<ErrorResponseMessage>
    ) : DomainErrorHolder(
        errors
    )

    // 401
    data class Authorization(
        val errors: List<ErrorResponseMessage>
    ) : DomainErrorHolder(
        errors
    )

    // 422
    data class Validation(
        val errors: List<ErrorResponseMessage>
    ) : DomainErrorHolder(
        errors
    )

    // 404
    data class NotFound(
        val errors: List<ErrorResponseMessage>
    ) : DomainErrorHolder(
        errors
    )

    // 429
    data class RateLimit(
        val errors: List<ErrorResponseMessage>
    ) : DomainErrorHolder(
        errors
    )

}
