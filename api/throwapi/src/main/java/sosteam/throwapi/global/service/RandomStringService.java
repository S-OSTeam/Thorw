package sosteam.throwapi.global.service;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomStringService {

    public static String generateKey(int length) {
        return RandomStringUtils.random(length, true, true);
    }
}
