package com.onyshkiv.util.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    private final static  String LOGIN_REGEX = "^(?=.{4,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
    private final static String PHONE_REGEX ="^(?:\\+38)?(?:\\(0[0-9]{2}\\)[ .-]?[0-9]{3}[ .-]?[0-9]{2}[ .-]?[0-9]{2}|0[0-9]{2}[ .-]?[0-9]{3}[ .-]?[0-9]{2}[ .-]?[0-9]{2}|0[0-9]{2}[0-9]{7})$";
    private final static String EMAIL_REGEX = "^([\\w\\-\\.]+)@([\\w\\-\\.]+)\\.([a-zA-Z]{2,5})$";
    private final static String NAME_REGEX = "^[a-zA-Zа-яА-Я\\\\s]{2,20}$";
//    private final static String ISBN_REGEX = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$";
    private final static String ISBN_REGEX = "^(?=(?:\\D?\\d){10}(?:(?:\\D?\\d){3})?$)[\\d-]+?$";
    private final static String PASSWORD_REGEX = "^[A-Za-z0-9_-]{6,18}$";

    public static boolean validateLogin (String login){
        return Pattern.matches(LOGIN_REGEX,login);
    }

    public static boolean validateEmail (String email){
        return Pattern.matches(EMAIL_REGEX,email);
    }

    public static boolean validateName (String name){
        return Pattern.matches(NAME_REGEX,name);
    }

    public static boolean validatePhone (String phone){
        return Pattern.matches(PHONE_REGEX,phone);
    }
    public static boolean validateIsbn (String isbn){
        return Pattern.matches(ISBN_REGEX,isbn);
    }
    public static boolean validatePassword (String password){
        return Pattern.matches(PASSWORD_REGEX,password);
    }


}
