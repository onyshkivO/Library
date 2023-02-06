package com.onyshkiv.DAO;

import java.util.ResourceBundle;

public class ApplicationResourceBundle {
    public static ResourceBundle resourceBundle = ResourceBundle.getBundle("datasource");

    public static void setTestBundle() {
        resourceBundle = ResourceBundle.getBundle("datasource_test");
    }

    private ApplicationResourceBundle() {
    }


}
