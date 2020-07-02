package com.example.civilservantapp.model.loan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;




@SuppressWarnings("all")
public class SendLoansuccess {
    @SerializedName("response")
    @Expose
    private final String response;

    private final Message message;

    public SendLoansuccess(String response, Message message) {
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
        @Expose
        private final List<String> data;

        public Message(List<String> data) {
            this.data = data;
        }

        public List<String> getData() {
            return data;
        }
    }
}
