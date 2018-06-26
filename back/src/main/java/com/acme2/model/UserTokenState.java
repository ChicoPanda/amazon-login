package com.acme2.model;

import lombok.Data;

@Data
public class UserTokenState {
	
    private String accessToken;
    private Long expiresIn;

    public UserTokenState() {
        this.accessToken = null;
        this.expiresIn = null;
    }

    public UserTokenState(String accessToken, long expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }


}
