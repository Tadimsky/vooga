package arcade.games;

public class Comment {

    private double myRating;
    private String myUser;
    private String myComment;

    public Comment (double rating , String user, String comment) {
        myRating = rating;
        myUser = user;
        myComment = comment;
    }

    protected double getRating () {
        return myRating;
    }

    protected String getUser () {
        return myUser;
    }

    protected String getComment () {
        return myComment;
    }
    

}
