package uk.gov.justice.digital.hmpps.digitalprisonreportinglib.config

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import uk.gov.justice.digital.hmpps.digitalprisonreportinglib.data.LocalDateTimeTypeAdaptor
import java.time.LocalDateTime

@Configuration
class DefinitionGsonConfig {

  @Bean("dprDefinitionGson")
  fun definitionGson(
    localDateTimeTypeAdaptor: LocalDateTimeTypeAdaptor,
  ): Gson = GsonBuilder()
    .registerTypeAdapter(LocalDateTime::class.java, localDateTimeTypeAdaptor)
    .create()
}
