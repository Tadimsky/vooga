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

    public double getRating () {
        return myRating;
    }

    public String getUser () {
        return myUser;
    }

    public String getComment () {
        return myComment;
    }
    

}
