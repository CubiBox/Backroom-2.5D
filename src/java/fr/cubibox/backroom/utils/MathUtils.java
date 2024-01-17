package fr.cubibox.backroom.utils;

import java.math.BigDecimal;

public class MathUtils {
    public static float add(float a, float b) {
        BigDecimal bda = BigDecimal.valueOf(a);
        BigDecimal bdb = BigDecimal.valueOf(b);

        return bda.add(bdb).floatValue();
    }
}
