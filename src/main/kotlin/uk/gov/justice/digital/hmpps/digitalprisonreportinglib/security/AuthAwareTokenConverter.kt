package uk.gov.justice.digital.hmpps.digitalprisonreportinglib.security

import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.oauth2.jwt.Jwt

interface AuthAwareTokenConverter : Converter<Jwt, AbstractAuthenticationToken> {

  override fun convert(jwt: Jwt): AuthAwareAuthenticationToken
}