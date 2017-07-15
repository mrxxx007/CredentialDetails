package CredentialDetails.util;

import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Utility class to support cipher methods
 */
public final class CryptoUtil {
    public static final String AES_TRANSFORMATION = "AES/CBC/PKCS5Padding";
    public static final String AES_ALGORITHM = "AES";

    public static SecretKeySpec getAESSecretKey(String password) {
        final byte[] passwordHash = DigestUtils.md5(password);
        return new SecretKeySpec(passwordHash, AES_ALGORITHM);
    }

    public static IvParameterSpec getAESParameters(String password) {
        final byte[] passwordHash = DigestUtils.md5(password);
        return new IvParameterSpec(passwordHash);
    }
}
