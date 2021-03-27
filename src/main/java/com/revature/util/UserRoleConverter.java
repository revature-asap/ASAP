package com.revature.util;

import com.revature.entities.UserRole;

import javax.persistence.AttributeConverter;

/**
 * Converter class for the user role
 */
public class UserRoleConverter implements AttributeConverter<UserRole, String> {

    /**
     * converts the user role into a string with an uppercase
     * @param userRole user role
     * @return returns a user role in the form of a string with the first letter capitalize
     */
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

    /**
     * Converting the string of user role back into an ENUM that is the user role
     * @param s string of user role
     * @return returns the user role ENUM
     */
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
