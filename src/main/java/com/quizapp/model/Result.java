package com.quizapp.model;


public class Result {
    private int id;
    private int userId;
    private int quizId;
    private int score;
    private String username;

    public Result(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Result( String username ,int id, int userId, int quizId, int score) {
        this.username = username;
        this.id = id;
        this.userId = userId;
        this.quizId = quizId;
        this.score = score;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuizId() {
        return quizId;
    }
    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Result{" +
                "userId=" + userId +
                "username=" + username +
                ", quizId=" + quizId +
                ", score=" + score +
                '}';
    }
}
