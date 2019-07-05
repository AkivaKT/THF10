package com.byui.thf10;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {

        Calendar c1 = Calendar.getInstance();
        c1.set(1994, 4, 12);

        Account a1 = new Account();
        a1.setFirstName("Keith");
        a1.setUserName("admin");
        a1.setPassword("1234");
        try {
            a1.encrypt();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<JsonConvertible> a = new ArrayList<>();
        a.add(a1);

        String pass = "1234";

        System.out.println(a1.checkPassword(pass));
    }
}