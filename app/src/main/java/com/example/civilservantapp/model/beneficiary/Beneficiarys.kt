package com.example.civilservantapp.model.beneficiary

import java.io.Serializable

data class Beneficiarys(
    var name: String?="",
    var nrc: String?="",
    var fathername: String?="",
    var relationship: String?="",
    var age: Int?=0,
    var percentage: Double?=0.0
): Serializable