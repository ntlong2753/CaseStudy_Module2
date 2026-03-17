package util;

public class ClassNameValidator {
    private static final String CLASSNAME_REGEX = "^[A-Z0-9]{3,6}$";

    public static boolean checkClassName(String className) {
        if (className == null || className.trim().isEmpty()) {
            return false;
        }
        return className.matches(CLASSNAME_REGEX);
    }
}
