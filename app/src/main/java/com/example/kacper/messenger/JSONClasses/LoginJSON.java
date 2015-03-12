package com.example.kacper.messenger.JSONClasses;

/**
 * Created by kacper on 26.01.15.
 */
public class LoginJSON {

    String login;
    String password;
    int protocolVersion;
    public LoginJSON(String login, String password, int protocolVersion){
        this.login = login;
        this.password = password;
        this.protocolVersion = protocolVersion;
    }
}
