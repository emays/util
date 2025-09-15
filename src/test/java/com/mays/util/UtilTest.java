package com.mays.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilTest {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(UtilTest.class);

	@Test
	public void tabDelimitedString() {
		assertEquals("1\t2", Util.tabDelimitedString(1, 2));
	}

}
