package com.example.kacper.messenger.JSONClasses;

/**
 * Created by kacper on 26.01.15.
 */
public class TokenResponseJSON {
    String token;
    long expDate;

    public TokenResponseJSON(String token, long expireDate) {

        this.token = token;
        this.expDate = expireDate;
    }

    public String getToken() {
        return token;
    }

    public long getExpDate() {
        return expDate;
    }
}
