package com.example.civilservantapp.model.fullyear;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SendFullYear {
    @SerializedName("response")
    private final String response;
    @SerializedName("message")
    private final Message message;

    public SendFullYear(String response, Message message) {
        this.response = response;
        this.message = message;
    }

    public String getResponse() {
        return response;
    }

    public Message getMessage() {
        return message;
    }

    public static class Message {
        @SerializedName("data")
        private final List<String> data;

        public Message(List<String> data) {
            this.data = data;
        }

        public List<String> getData() {
            return data;
        }
    }
}
