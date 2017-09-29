package com.itboye.pondteam.utils;

/**
 * Created by itboye on 2017/2/24.
 */

public class EmailUtil {
    /**
     * 验证邮箱格式是否正确
     */
    public static boolean emailValidation(String email) {
        String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        return email.matches(regex);
    }
}
