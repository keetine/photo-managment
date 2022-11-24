package com.photo.photo_backend.utils;


public class PasswordUtil {
    /**
     * 加密字符串
     * @param password 明文
     * @return 加密后的字符串
     */
    public static String encrypt(String password) {
        // 加密方式：将密码的每个字符的ASCII码值乘以2，再将结果转为16进制
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            sb.append(Integer.toHexString(password.charAt(i) * 2));
        }
        return sb.toString();
    }

    /**
     * 解密字符串
     * @param password 密文
     * @return 解密后的字符串
     */
    public static String decrypt(String password) {
        // 解密方式：将密码的每个字符的ASCII码值除以2，再将结果转为10进制
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < password.length(); i += 2) {
            sb.append((char) (Integer.parseInt(password.substring(i, i + 2), 16) / 2));
        }
        return sb.toString();
    }

    /**
     * 验证密码
     * @param password 明文
     * @param encryptPassword 密文
     * @return 验证结果
     */
    public static boolean verify(String password, String encryptPassword) {
        return encrypt(password).equals(encryptPassword);
    }

}


