package com.first.myapplication.collegemess;

public class feed {

    String feedbackID;
    String messType;
    String feedbackGenre;
    String actualFeedback;
    String emailID;


    public feed(){

    }

    public feed(String feedbackID, String messType, String feedbackGenre, String actualFeedback, String emailID) {
        this.feedbackID = feedbackID;
        this.messType = messType;
        this.feedbackGenre = feedbackGenre;
        this.actualFeedback = actualFeedback;
        this.emailID = emailID;
    }

    public String getFeedbackID() {
        return feedbackID;
    }

    public String getMessType() {
        return messType;
    }

    public String getFeedbackGenre() {
        return feedbackGenre;
    }

    public String getActualFeedback() {
        return actualFeedback;
    }

    public String getEmailID() {
        return emailID;
    }
}
