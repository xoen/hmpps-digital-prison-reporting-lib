package uk.gov.justice.digital.hmpps.digitalprisonreportinglib.data.model.policyengine

import com.google.gson.annotations.SerializedName

enum class Effect {
  @SerializedName("permit")
  PERMIT,
  @SerializedName("deny")
  DENY,
}
