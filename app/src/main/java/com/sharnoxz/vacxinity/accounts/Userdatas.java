package com.sharnoxz.vacxinity.accounts;


public class Userdatas {
    String name,gender,address,pinCode,phone,profilePic,userUid;

    public Userdatas(String name, String gender, String address, String pinCode, String phone, String profilePic, String userUid) {
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.pinCode = pinCode;
        this.phone = phone;
        this.profilePic = profilePic;
        this.userUid = userUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

}
