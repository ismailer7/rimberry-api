package io.idev.rimberry.utils;

import java.io.File;

public class ImageUtils {

	public static String getAvatar() {
		File file = new File("resources/static/images/male.png");
		String absolutePath = file.getAbsolutePath();
		return absolutePath;
	}
	
}
