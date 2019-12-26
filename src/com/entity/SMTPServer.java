/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entity;

/**
 *
 * @author leanh
 */
public class SMTPServer {
    private String server;
    private String authentication;
    private String port;
    private String provider;

    public SMTPServer(String server, String authentication, String port, String provider) {
        this.server = server;
        this.authentication = authentication;
        this.port = port;
        this.provider = provider;
    }
   
    public SMTPServer() {
    }

    public SMTPServer(String server, String authentication, String port) {
        
        this.server = server;
        this.authentication = authentication;
        this.port = port;
        
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    
    
    @Override
    public String toString() {
        return this.server + "(" + this.authentication + ")";
    }
    
    
    
}
