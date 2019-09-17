package edible.simple.payload.auth;

import edible.simple.model.User;

public class CurrentUserResponse{
    private Long id;
    private String username;
    private String name;
    private String email;
    private String imageurl;
    private String bio;
    private String phonenumber;
    private int rating;

    public CurrentUserResponse() {
    }

    public CurrentUserResponse(Long id, String username, String name, String email, String imageurl, String bio, String phonenumber, int rating) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.imageurl = imageurl;
        this.bio = bio;
        this.phonenumber = phonenumber;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
