package com.example.retrofitexample

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Receiver {

    @SerializedName("@type")
    @Expose
    var _type: String? = null
    @SerializedName("phoneNumber")
    @Expose
    var phoneNumber: String? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param phoneNumber
     * @param _type
     */
    constructor(_type: String, phoneNumber: String) : super() {
        this._type = _type
        this.phoneNumber = phoneNumber
    }

}