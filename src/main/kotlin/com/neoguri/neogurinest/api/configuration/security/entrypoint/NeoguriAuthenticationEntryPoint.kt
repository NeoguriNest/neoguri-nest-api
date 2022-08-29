package com.neoguri.neogurinest.api.configuration.security.entrypoint

import com.fasterxml.jackson.databind.ObjectMapper
import com.neoguri.neogurinest.api.presentation.error.ErrorResponseDto
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Slf4j
class NeoguriAuthenticationEntryPoint : AuthenticationEntryPoint {

    private val objectMapper: ObjectMapper = ObjectMapper()

    private val logger: Logger = LoggerFactory.getLogger(this@NeoguriAuthenticationEntryPoint::class.java)

    override fun commence(request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?) {
        logger.info("Exception caught while processing authentication, {}", authException!!.message)
        val status = HttpStatus.UNAUTHORIZED

        (response as HttpServletResponse)
        response.status = status.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        objectMapper.writeValue(response.writer, ErrorResponseDto.create(status, authException.message))
    }
}