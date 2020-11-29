package com.book.movieticketbooking.useractivity.model;

public class Userprofile {
    public String userName;
    public String userDob;
    public String userEmail;
    public String userMobile;
    public String userAddress;
    public String usertype;
    public String userProfilePic;

    public Userprofile(){
    }

    public Userprofile(String userName, String userDob, String userEmail, String userMobile, String userAddress,String usertype, String userProfilePic) {
        this.userName = userName;
        this.userDob = userDob;
        this.userEmail = userEmail;
        this.userMobile = userMobile;
        this.userAddress = userAddress;
        this.usertype = usertype;
        this.userProfilePic = userProfilePic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDob() {
        return userDob;
    }

    public void setUserDob(String userDob) {
        this.userDob = userDob;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUsertype() { return usertype; }

    public void setUsertype(String usertype) { this.usertype = usertype; }

    public String getUserProfilePic() { return userProfilePic; }

    public void setUserProfilePic(String userProfilePic) { this.userProfilePic = userProfilePic; }
}
