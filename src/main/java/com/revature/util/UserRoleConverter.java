package com.revature.util;

import com.revature.entities.UserRole;

import javax.persistence.AttributeConverter;

public class UserRoleConverter implements AttributeConverter<UserRole, String> {
    @Override
    public String convertToDatabaseColumn(UserRole userRole) {
        if(userRole == null){
            return null;
        }
        switch(userRole){
            case BASIC:
                return "Basic";
            case ADMIN:
                return "Admin";
            case DEV:
                return "Dev";
            default:
                throw new IllegalArgumentException(userRole + " invalid!");

        }
    }

    @Override
    public UserRole convertToEntityAttribute(String s) {
        if (s.isEmpty()) {
            return null;
        }
        switch (s) {
            case "Basic":
                return UserRole.BASIC;
            case "Admin":
                return UserRole.ADMIN;
            case "Dev":
                return UserRole.DEV;
            default:
                throw new IllegalArgumentException(s + " not supported");

        }
    }
}
