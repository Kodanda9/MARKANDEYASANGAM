package com.cpetsolu.administrator.markandeyasangam.fragment;

/**
 * Created by Admin on 9/21/2018.
 */

public class Login {
    private String number;
    private String password;
    private Integer status;

    public String getNumber() {
        return number;
    }

    public String getPassword() {
        return password;
    }

    public Integer getStatus() {
        return status;
    }

    public Login(String number, String password, int status) {
        this.number=number;

        this.password=password;
        this.status=status;
    }
}
