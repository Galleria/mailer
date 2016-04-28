package com.main.entities;

import java.util.List;

public class Email {
    private List<String> toEmails;
    private String topic;
    private String body;

    public Email(List<String> toEmails, String topic, String body) {
        this.toEmails = toEmails;
        this.topic = topic;
        this.body = body;
    }

    public List<String> getToEmails() {
        return toEmails;
    }

    public void setToEmails(List<String> toEmails) {
        this.toEmails = toEmails;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
