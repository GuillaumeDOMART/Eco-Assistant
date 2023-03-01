package com.ecoassitant.back.utils;

import java.util.Random;

/**
 * Class that group method to generate string
 */
public class StringGeneratorUtils {

    /**
     * Randomly choose a char within a string
     *
     * @param string string to choose a char from
     * @return the randomly choosen char
     */
    private static char choose(String string) {
        var random = new Random();
        return string.charAt(random.nextInt(string.length()));
    }

    /**
     * Function to generate a random string
     *
     * @param length the length of the random string
     * @return the random string
     */
    public static String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        var sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(choose(chars));
        }
        return sb.toString();
    }

    /**
     * Function to generate a random adequate password
     * <p>
     * length 12
     * at least:
     * 1 lowercase
     * 1 uppercase
     * 1 special character
     * 1 digit
     *
     * @return the password validating the conditions
     */
    public static String generateRandomPassword() {
        var upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        var lower = "abcdefghijklmnopqrstuvwxyz";
        var digit = "0123456789";
        var specials = "#?!@$%^&*\\-_";

        var constraints = new String[]{upper, lower, digit, specials};
        var random = new Random();
        var sb = new StringBuilder();
        var pwdLength = 14;
        int i = 0;
        for (; i < 4; i++) {
            sb.append(choose(constraints[i]));
        }

        for (; i < pwdLength; i++) {
            sb.append(choose(constraints[random.nextInt(4)]));
        }

        return sb.toString();
    }
}
