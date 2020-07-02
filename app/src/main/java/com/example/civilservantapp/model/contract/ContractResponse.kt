package com.example.civilservantapp.model.contract

import com.google.gson.annotations.SerializedName

data class ContractResponse(
    @SerializedName("download_link")
    var download_link: DownloadLink?=null,
    @SerializedName("path")
    var path: String=""
)

data class DownloadLink(
    @SerializedName("url")
    var url: String?=""
)