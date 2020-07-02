package com.example.retrofitexample

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class OtpFormat
/**
 *
 * @param receiver
 * @param sender
 * @param sentTime
 * @param id
 * @param type
 * @param content
 * @param status
 */(
    id: String,
    message: String,
    type: String,
    status: String,
    sentTime: String,
    content: String,
    sender: Sender,
    receiver: List<Receiver>
) {

    @SerializedName("id")
    @Expose
    var id: String? = id
    @SerializedName("message")
    @Expose
    var message : String? = message
    @SerializedName("type")
    @Expose
    var type: String? = type
    @SerializedName("status")
    @Expose
    var status: String? = status
    @SerializedName("sentTime")
    @Expose
    var sentTime: String? = sentTime
    @SerializedName("content")
    @Expose
    var content: String? = content
    @SerializedName("sender")
    @Expose
    var sender: Sender? = sender
    @SerializedName("receiver")
    @Expose
    var receiver: List<Receiver>? = receiver

}