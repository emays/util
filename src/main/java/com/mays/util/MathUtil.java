package com.mays.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtil {

	private MathUtil() {
	}

	public static BigDecimal round(double value, int scale) {
		return new BigDecimal(value).setScale(scale, RoundingMode.HALF_UP);
	}

}
