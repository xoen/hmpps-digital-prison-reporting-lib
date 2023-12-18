package uk.gov.justice.digital.hmpps.digitalprisonreportinglib.security

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TokenConverterAutoConfig {

  @Bean
  @ConditionalOnMissingBean(DprAuthAwareTokenConverter::class)
  fun dprAuthAwareTokenConverter(
    caseloadProvider: CaseloadProvider,
  ): DprAuthAwareTokenConverter {
    return DefaultDprAuthAwareTokenConverter(caseloadProvider)
  }
}
