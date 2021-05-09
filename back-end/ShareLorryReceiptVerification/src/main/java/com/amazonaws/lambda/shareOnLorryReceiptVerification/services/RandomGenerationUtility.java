package com.amazonaws.lambda.shareOnLorryReceiptVerification.services;

import java.util.Date;
import java.util.Random;

public class RandomGenerationUtility {

	public static String generateKey(int id) {

		int randomNum = new Random().nextInt(999999 - 100000 + 1) + 100000;
		String key = "00X" + id + new Date().toString() + String.valueOf(randomNum);
		key = key.replace(" ", "_");
		return key;
	}
}
