package com.matija.softtehn.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
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
