package com.example.make.service.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HostDTO {

    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;

    @SerializedName("list")
    private List<passengers> u_list;


    @SerializedName("hostId")
    private Long host_id;

    public Long getHost_id() {
        return host_id;
    }

    public void setHost_id(Long host_id) {
        this.host_id = host_id;
    }

    public List<passengers> getU_list() {
        return u_list;
    }

    public void setU_list(List<passengers> u_list) {
        this.u_list = u_list;
    }

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

    public class passengers{
        @SerializedName("id")
        private Long id;
        @SerializedName("passengerUserId")
        private passengerUserId list;
        @SerializedName("startNode")
        private int startNode;

        public int getStartNode() {
            return startNode;
        }

        public void setStartNode(int startNode) {
            this.startNode = startNode;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public passengerUserId getList() {
            return list;
        }

        public void setList(passengerUserId list) {
            this.list = list;
        }

        public class passengerUserId{
            @SerializedName("id")
            private Long id;
            @SerializedName("phone")
            private String phone;
            @SerializedName("name")
            private String name;
            @SerializedName("password")
            private String password;

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
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
}

