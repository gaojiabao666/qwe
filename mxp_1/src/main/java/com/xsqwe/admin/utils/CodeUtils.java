package com.xsqwe.admin.utils;

import java.security.SecureRandom;
import java.util.UUID;

public class CodeUtils {

    private static String[] chars = new String[]{"A", "B", "C", "D", "E", "F",
            "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private static String[] numChars = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public static String generateCode() {

        StringBuffer codeBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        int a = 0;
        int b = 0;
        for (int j = 0; j < 8; j++) {
            SecureRandom riii = new SecureRandom();
            int nextInt = riii.nextInt(10);
            String str = uuid.substring(j * 4, j * 4 + 4);
            int par = Integer.parseInt(str, 16);
            if (nextInt % 2 == 0) {
                if (a >= 4) {
                    codeBuffer.append(chars[par % 24]);
                    b++;
                } else {
                    //数字
                    codeBuffer.append(numChars[par % 10]);
                    a++;
                }
            } else {
                if (b >= 4) {
                    //数字
                    codeBuffer.append(numChars[par % 10]);
                    a++;
                } else {
                    codeBuffer.append(chars[par % 24]);
                    b++;
                }
            }
        }
        return codeBuffer.toString();
    }
}
