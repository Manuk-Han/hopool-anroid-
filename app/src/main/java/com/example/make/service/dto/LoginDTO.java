package com.example.make.service.dto;

import com.google.gson.annotations.SerializedName;

public class LoginDTO {

    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;
    @SerializedName("time")
    private String time;
    @SerializedName("account")
    private Account account;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public class Account {
        @SerializedName("id")
        private int id;
        @SerializedName("phone")
        private String phone;
        @SerializedName("name")
        private String name;
        @SerializedName("password")
        private String password;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
