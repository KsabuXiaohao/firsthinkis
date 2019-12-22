package com.thinkis.modules.sys.security;

public enum LoginType {

	USER("user"),  ADMIN("admin");

    private String type;

    private LoginType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type.toString();
    }
    
}
