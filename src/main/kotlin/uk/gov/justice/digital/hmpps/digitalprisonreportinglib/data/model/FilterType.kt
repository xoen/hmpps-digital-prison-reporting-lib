package uk.gov.justice.digital.hmpps.digitalprisonreportinglib.data.model

import com.google.gson.annotations.SerializedName

enum class FilterType {
  @SerializedName("radio")
  Radio,
  @SerializedName("select")
  Select,
  @SerializedName("daterange")
  DateRange,
  @SerializedName("autocomplete")
  AutoComplete,
}
