package org.aria.okhttp;

import org.junit.Test;

public class OkUtilsTest {

    @Test
	public void testFilename() {
		System.out.println("https://download-app.musixmatch.com/");
		System.out.println(org.aria.okhttp.OkUtils.Filename("https://download-app.musixmatch.com/"));
	}

}