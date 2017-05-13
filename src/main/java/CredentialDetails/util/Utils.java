package CredentialDetails.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Admin on 25.09.2016.
 */
public final class Utils {
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static <T> Set<T> arrayToSet(T[] array) {
        Set<T> set = new HashSet<>(array.length);
        Collections.addAll(set, array);
        return set;
    }
}
