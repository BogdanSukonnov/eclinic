package com.bogdansukonnov.eclinic.security;

public enum AuthorityType {

    ROLE_ADMIN("ADMIN"),
    ROLE_DOCTOR("DOCTOR"),
    ROLE_NURSE("NURSE");

    private final String role;

    AuthorityType(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}
