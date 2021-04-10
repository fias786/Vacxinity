package com.sharnoxz.vacxinity.accounts;

public class Photo {

    String pic,userUid;

    public Photo(String pic, String userUid) {
        this.pic = pic;
        this.userUid = userUid;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }
}
