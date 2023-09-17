package org.alexdev.finlay.game.encryption;

import java.util.concurrent.ThreadLocalRandom;

public class SecretKeyGenerator {
    public static String generate(int len) {
        StringBuilder result = new StringBuilder();

        char[] numbers = new char[] { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };

        for (int i = 0; i < len; i++) {
            result.append(numbers[ThreadLocalRandom.current().nextInt(numbers.length)]);
        }
        return result.toString();
    }
}