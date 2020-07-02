package com.example.retrofitexample

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Sender {

    @SerializedName("@type")
    @Expose
    var _type: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param _type
     * @param name
     */
    constructor(_type: String, name: String) : super() {
        this._type = _type
        this.name = name
    }

}