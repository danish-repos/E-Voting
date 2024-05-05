package com.example.e_voting.Classes;

public class Post {

    String nameCandidate;
    String topic, text;
    int likes, dislikes;

    public Post(){

    }

    public Post(String nameCandidate,String topic, String text, int likes, int dislikes) {
        this.nameCandidate = nameCandidate;
        this.topic = topic;
        this.text = text;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public String getNameCandidate(){
        return nameCandidate;
    }

    public void setNameCandidate(String nameCandidate){
        this.nameCandidate = nameCandidate;

    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }
}
