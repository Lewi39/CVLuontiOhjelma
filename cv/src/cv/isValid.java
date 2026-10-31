package cv;

// import java.util.Collection;
import java.util.regex.Pattern;

public class isValid
{
	/**
	 * Antaa käyttäjän antaa etunimeesi vain pohjoismaisia aakkosia
	 */
	boolean isValidFName(String str)
	{
		var _str = "^[a-zA-ZåäöÅÄÖ]{2,}$";
		var _pattern = Pattern.compile(_str);
		return str != null && _pattern.matcher(str).matches();
	}

	/**
	 * Antaa käyttäjän antaa sukunimeensä vain pohjoismaisia aakkosia
	 */
	boolean isValidLName(String str)
	{
		var _str = "^[a-zA-ZåäöÅÄÖ]{2,}$";
		var _pattern = Pattern.compile(_str);
		return str != null && _pattern.matcher(str).matches();
	}

	/**
	 * Palauttaa Boolean arvon true tai false, mikäli annettu stringi sisältää
	 * puhelinnumero formaattia muistuttavat numerosarjan
	 * 
	 */
	boolean isValidPhone(String str)
	{
		// https://uibakery.io/regex-library/phone-number-java
		// Sallii myös + () # /
		// var _str = "^\\+-()#/?[0-9][0-9]{2,}$";
		var test = 
				"^\\+?\\(?\\d{1,4}\\)?" 
				+ "[-.\\s]?\\(?\\d{1,3}?\\)?" 
				+ "[-.\\s]?\\d{1,4}" 
				+ "[-.\\s]?\\d{1,4}"
		        + "[-.\\s]?\\d{1,9}\\)?$";
		/**
		 * var test = "^\\+?\\(?\\d{1,4}\\)?"
		 * // + "[-.\\s]?\\(?\\d{1,3}?\\)?"
		 * // + "[-.\\s]?\\d{1,4}"
		 * // + "[-.\\s]?\\d{1,4}"
		 * // + "[-.\\s]?\\d{1,9}\\)?$";
		 * // var test =
		 * // "^\\+?\\d{1,4}?[-.\\s]?" +
		 * // "(?:\\(?\\(\\d{1,3}?\\)|\\d{1,4})" +
		 * // "[-.\\s]?\\d{1,4}" +
		 * // "(?:[-.\\s]?\\d{1,4})? " +
		 * // "(?:[-.\\s]?\\d{1,9}) $";
		 */
		var _pattern = Pattern.compile(test);
		return str != null && _pattern.matcher(str).matches();
	}

	boolean isValidString(String str)
	{
		var regex = "^.{2,}$";
		var _pattern = Pattern.compile(regex);
		return str != null && _pattern.matcher(str).matches();
	}

	/**
	 * https://www.geeksforgeeks.org/java/check-email-address-valid-not-java/ More
	 * info of Regular Expressions
	 * https://www.geeksforgeeks.org/java/regular-expressions-in-java/#:~:text=
	 * Regex%20Quantifiers%20/%20Metacharacters
	 * 
	 * Alemassa linkissä on selitetty, miten Regular expressionit toimivat
	 */
	boolean isValidEmail(String str)
	{
		var _Emailregex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
		Pattern _pattern = Pattern.compile(_Emailregex);
		return str != null && _pattern.matcher(str).matches();
	}
}
