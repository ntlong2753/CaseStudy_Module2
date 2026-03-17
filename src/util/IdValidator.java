package util;

public class IdValidator {
    private static final String ID_REGEX = "^[a-zA-Z0-9]+$";

    public static boolean checkId(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }
        return id.matches(ID_REGEX);
    }
}
