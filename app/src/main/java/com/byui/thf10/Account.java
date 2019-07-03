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


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String newPassword) {
        password = newPassword;
        try {
            encrypt();
        } catch (Exception e) {
            Log.i("Login","Incorrect password setting");
        }
    }

    public String getPassword(){return password;}

    public String getSalt() {
        return salt;
    }

    public void setSalt(String newSalt) {
        salt = newSalt;
    }

    public String getHash() {
        return hash;
    }

    private void encrypt()throws Exception {
        Encryption.hashUserPassword(this);
    }
    public void setHash(String hash){this.hash = hash; }

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