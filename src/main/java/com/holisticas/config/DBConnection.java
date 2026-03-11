/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holisticas.config;

import javax.servlet.ServletContext;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private final String jdbcURL;
    private final String jdbcUsername;
    private final String jdbcPassword;

    public DBConnection(ServletContext context) {
        this.jdbcURL = context.getInitParameter("jdbcURL");
        this.jdbcUsername = context.getInitParameter("jdbcUserName");
        this.jdbcPassword = context.getInitParameter("jdbcPassword");
    }

    public Connection getConnection() throws Exception {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }
}