package com.matija.softtehn.model;

import javax.persistence.Id;

public class RevokedToken {
    @Id
    private String revokedTokenId;

    public RevokedToken() {}

    public RevokedToken(String revokedTokenId) {
        this.revokedTokenId = revokedTokenId;
    }

    public String getRevokedTokenId() {
        return revokedTokenId;
    }

    public void setRevokedTokenId(String revokedTokenId) {
        this.revokedTokenId = revokedTokenId;
    }
}
