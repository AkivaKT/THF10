package com.byui.thf10;

import android.util.Log;

import static android.content.ContentValues.TAG;

public class Account extends JsonConvertible {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String hash;
    private String salt;
    private String date;


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword(){ return password;}

    public String getSalt() { return salt; }

    public String getHash() { return hash; }

    public String getDate() { return date; }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String newPassword) { password = newPassword; }

    public void setSalt(String newSalt) { salt = newSalt; }

    public void setHash(String hash){this.hash = hash; }

    public void encrypt()throws Exception {
        Encryption.hashUserPassword(this);
    }

    public boolean checkPassword (String password){
        setPassword(password);
        try {
            if (Encryption.verifyPassword(this)){
                return true;}
            else{return false;}
        } catch (Exception e) {
            Log.e(TAG,"problem check password");
            return false;
        }
    }
}