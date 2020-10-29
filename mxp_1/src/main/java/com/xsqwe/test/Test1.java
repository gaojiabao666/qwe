package com.xsqwe.test;

import com.xsqwe.utils.FastCodeUtils;

public class Test1 {
    public static void main(String[] args) {
        String a = "15137222876";
        String encrypt = FastCodeUtils.encrypt(a);

        String decrypt = FastCodeUtils.decrypt("ymXcvCOaarZoVmrrQqSooQ==");
        System.out.println(encrypt);
        System.out.println(decrypt);
    }
}
