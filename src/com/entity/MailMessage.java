/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entity;

import java.io.File;

/**
 *
 * @author leanh
 */
public class MailMessage {
    private String from;
    private String to;
    private String message;
    private String subject;
    private File attachment;

    public MailMessage() {
    }

    public MailMessage(String from, String to, String message, String subject, File attachment) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.subject = subject;
        this.attachment = attachment;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public File getAttachment() {
        return attachment;
    }

    public void setAttachment(File attachment) {
        this.attachment = attachment;
    }

   
    
}
