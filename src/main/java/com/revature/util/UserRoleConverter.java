package com.revature.util;

import com.revature.entities.UserRole;

import javax.persistence.AttributeConverter;

/**
 * Converter class for the user role
 */
public class UserRoleConverter implements AttributeConverter<UserRole, String> {

    /**
     * converts an instance of {@code UserRole} into a string with an uppercase first letter
     * @param userRole a {@code UserRole} to be converted to a {@code String}
     * @return returns a user role in the form of a string with the first letter capitalized
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
     * Converting the string for a user role back into an ENUM 
     * @param s a string for a user role
     * @return an instance of a {@code UserRole} corresponding to the value passed in
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
