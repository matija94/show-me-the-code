package android.matija.com.audisrbija;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by matija on 3.2.17..
 */

public class Customer {

    private String firstName;
    private String lastName;
    private String eMail;

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

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    private static class EmailChecker {
        static boolean checkEmail(String email) {
            if (email==null || email.isEmpty()) return false;
            if (email.matches("[a-zA-Z]+@[a-zA-Z]+\\.(com|rs)")) {
                return true;
            }
            return false;
        }
    }


    public boolean areFieldsValid() {
        if (firstName == null|| firstName.isEmpty() || lastName==null || lastName.isEmpty() || !EmailChecker.checkEmail(this.eMail)) {
            return false;
        }
        return true;
    }
}
