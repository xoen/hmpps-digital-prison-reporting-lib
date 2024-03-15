package uk.gov.justice.digital.hmpps.digitalprisonreportinglib.data.model.policyengine

import com.google.gson.annotations.SerializedName

enum class PolicyType {
  @SerializedName("row-level")
  ROW_LEVEL,
  @SerializedName("access")
  ACCESS,
}
