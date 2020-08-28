package co.jp.api.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import co.jp.api.cmn.Constants;

@Component
public class AppUtils {

	private static final Logger logger = LoggerFactory.getLogger(AppUtils.class);
	
	public static Timestamp getDateNowTimestamp() {
		LocalDateTime localDateNow = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(localDateNow);
		return timestamp;
	}
	
	public static String getDateNow() {
		String dateNow = "";
		LocalDateTime localDateNow = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		dateNow = localDateNow.format(formatter);
		return dateNow;
	}
	/**
	 * StatusDate
	 * 	1: year
	 * 	2: month
	 * 	3: date
	 * 	4: hour
	 * 	5: minute
	 * 	6: second
	 * TimeAdd
	 * 
	 * @return
	 */
	public static String[] getDateStartAndDateEnd (int statusDate, int timeAdd) {
		String[] dateStr = new String[2];
		LocalDateTime localDateStart = LocalDateTime.now();
		LocalDateTime localDateEnd = null;
		if (statusDate == Constants.NUMBER_3) {
			localDateEnd = localDateStart.plusDays(timeAdd);
		}
		if (statusDate == Constants.NUMBER_4) {
			localDateEnd = localDateStart.plusHours(timeAdd);
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		dateStr[0] = localDateStart.format(formatter);
		dateStr[1] = localDateEnd.format(formatter);
		return dateStr;
	}
	
	public static String parseTimestampMinusToString(Timestamp date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDateTime = date.toLocalDateTime();
		LocalDateTime localDate = localDateTime.minus(Period.ofDays(Constants.NUMBER_1));
		String dateString = localDate.format(formatter);
		return dateString;
	}
	
	public static String parseDateNowYYYYMMDD() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime localDateNow = LocalDateTime.now();
		String dateString = localDateNow.format(formatter);
		return dateString;
	}
	
	public static Timestamp parseStringToTimestamp(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDateNow = LocalDateTime.parse(date, formatter);
		Timestamp timestamp = Timestamp.valueOf(localDateNow);
		return timestamp;
	}
	
	public static Date parseStringToDate(String dateStr) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date date = formatter.parse(dateStr);  
		return date;
	}

	public static String parseTimestampToString(Timestamp date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDateTime = date.toLocalDateTime();
		String dateString = localDateTime.format(formatter);
		return dateString;
	}

	
	public static LocalDateTime parseLocalDateTime(String datetime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(AppContants.YYYY_MM_DD);
		LocalDateTime localDate = LocalDateTime.parse(datetime, formatter);
		return localDate;
	}

	public static String sqlExcute(String fileName) {
		StringBuilder sb = new StringBuilder();
		String fileRead = AppContants.PATH_URL + AppContants.PATH_SQL + fileName;
		try (BufferedReader br = Files.newBufferedReader(Paths.get(fileRead))) {

			// read line by line
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append(AppContants.PATH_N);
			}

		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
		logger.info(sb.toString());
		return sb.toString();
	}

	public boolean isEmpty(String str) {
		return (str == null || str.length() == 0);
	}

	public boolean isRate(String cellValueStr) {
		if (isDouble(cellValueStr)) {
			double rate = Double.parseDouble(cellValueStr);
			return rate >= 0.0 && rate <= 100.0;
		}

		return false;
	}

	public boolean isDouble(String cellValueStr) {
		try {
			Double.parseDouble(cellValueStr);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static String parseStringToMd5(String str) {
		String strMd5 = null;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(str.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			strMd5 = number.toString(16);
		} catch (NoSuchAlgorithmException e) {
			logger.info("Parse md5 error");
			e.printStackTrace();
		}
		return strMd5;
	}
	
	/**
	 * Compare start date and end date
	 * 
	 * @param dateToken
	 * @param timestampDB
	 * @return
	 */
	public static boolean compareTimeDate (Date dateToken, Timestamp timestampDB) {
		LocalDateTime dateStart = parseDateToLocalDateTime(dateToken);
		LocalDateTime dateEnd = parseTimestampToLocalDateTime(timestampDB);
		int compareValue = dateStart.compareTo(dateEnd);
		if (compareValue > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public static LocalDateTime parseDateToLocalDateTime(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDateTime();
	}
	
	public static LocalDateTime parseTimestampToLocalDateTime(Timestamp dateToConvert) {
	    return dateToConvert.toLocalDateTime();
	}
	
	public static String randomString(int number) {
        String text = "";
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        for( int i=0; i < number; i++ ) {
        	int rndCharAt = random.nextInt(chars.length());
        	char rndChar = chars.charAt(rndCharAt);
        	text += rndChar;
         }
	    return text;
	}
	
	public static String formatEmail(String email) {
		String emailStr = email.replaceAll("%40", "@").replaceAll("=", "");
		return emailStr;
	}
}
