package com.techpoint.entities;

public class Reaction {
    public enum ReactionType {
        LIKE, DISLIKE
    }

    private final int userId;
    private final int postId;
    private final ReactionType type;

    public Reaction(int userId, int postId, ReactionType type) {
        this.userId = userId;
        this.postId = postId;
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public int getPostId() {
        return postId;
    }

    public ReactionType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Reaction{" +
                "userId=" + userId +
                ", postId=" + postId +
                ", type=" + type +
                '}';
    }
}
