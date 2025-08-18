package za.ac.cput.domain;


// POJO class for Review

/* Review.java
     Review POJO class
    Author: S.Malotana (221800662) */



public class Review {


    private int reviewID;
    private String rating;
    private String comment;

    protected Review() {
    }
    private Review(Builder builder) {
        this.reviewID = builder.reviewID;
        this.rating = builder.rating;
        this.comment = builder.comment;
    }

    public int getReviewID() {
        return reviewID;
    }

    public String getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }


    public static class Builder {
        private int reviewID;
        private String rating;
        private String comment;


        public Builder setReviewID(int reviewID) {
            this.reviewID = reviewID;
            return this;
        }


        public Builder setRating(String rating) {
            this.rating = rating;
            return this;
        }


        public Builder setComment(String comment) {
            this.comment = comment;
            return this;
        }


        public Review build() {
            return new Review(this);
        }
    }
}
