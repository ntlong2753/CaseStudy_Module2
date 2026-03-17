package util;

public class UsernameValidator {
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9_]{3,20}$";

    public static boolean checkUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        return username.matches(USERNAME_REGEX);
    }
}
