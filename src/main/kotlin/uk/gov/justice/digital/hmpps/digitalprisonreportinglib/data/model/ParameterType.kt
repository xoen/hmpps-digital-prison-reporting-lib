package uk.gov.justice.digital.hmpps.digitalprisonreportinglib.data.model

import com.google.gson.annotations.SerializedName

enum class ParameterType {
  @SerializedName("string")
  String,
  @SerializedName("date")
  Date,
  @SerializedName("long")
  Long,
  @SerializedName("time")
  Time,
}
