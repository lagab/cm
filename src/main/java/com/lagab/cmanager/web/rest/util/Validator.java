package com.lagab.cmanager.web.rest.util;

import com.lagab.cmanager.web.rest.errors.FileExtensionException;
import com.lagab.cmanager.web.rest.errors.FileNameException;
import com.lagab.cmanager.web.rest.errors.FileSizeException;
import com.lagab.cmanager.web.rest.errors.FolderNameException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    /**
     * Returns <code>true</code> if the boolean arrays are equal.
     *
     * @param  booleanArray1 the first boolean array
     * @param  booleanArray2 the second boolean array
     * @return <code>true</code> if the booleans arrays are equal; <code>false
     *         </code>otherwise
     */
    public static boolean equalsSorted(
        boolean[] booleanArray1, boolean[] booleanArray2) {

        Boolean[] booleanObjArray1 = ArrayUtil.toArray(booleanArray1);

        Arrays.sort(booleanObjArray1);

        Boolean[] booleanObjArray2 = ArrayUtil.toArray(booleanArray2);

        Arrays.sort(booleanObjArray2);

        return Arrays.equals(booleanObjArray1, booleanObjArray2);
    }

    /**
     * Returns <code>true</code> if the byte arrays are equal.
     *
     * @param  byteArray1 the first byte array
     * @param  byteArray2 the second byte array
     * @return <code>true</code> if the byte arrays are equal; <code>false
     *         </code>otherwise
     */
    public static boolean equalsSorted(byte[] byteArray1, byte[] byteArray2) {
        byteArray1 = ArrayUtil.clone(byteArray1);

        Arrays.sort(byteArray1);

        byteArray2 = ArrayUtil.clone(byteArray2);

        Arrays.sort(byteArray2);

        return Arrays.equals(byteArray1, byteArray2);
    }

    /**
     * Returns <code>true</code> if the char arrays are equal.
     *
     * @param  charArray1 the first char array
     * @param  charArray2 the second char array
     * @return <code>true</code> if the char arrays are equal; <code>false
     *         </code>otherwise
     */
    public static boolean equalsSorted(char[] charArray1, char[] charArray2) {
        charArray1 = ArrayUtil.clone(charArray1);

        Arrays.sort(charArray1);

        charArray2 = ArrayUtil.clone(charArray2);

        Arrays.sort(charArray2);

        return Arrays.equals(charArray1, charArray2);
    }

    /**
     * Returns <code>true</code> if the double arrays are equal.
     *
     * @param  doubleArray1 the first double array
     * @param  doubleArray2 the second double array
     * @return <code>true</code> if the double arrays are equal; <code>false
     *         </code>otherwise
     */
    public static boolean equalsSorted(
        double[] doubleArray1, double[] doubleArray2) {

        doubleArray1 = ArrayUtil.clone(doubleArray1);

        Arrays.sort(doubleArray1);

        doubleArray2 = ArrayUtil.clone(doubleArray2);

        Arrays.sort(doubleArray2);

        return Arrays.equals(doubleArray1, doubleArray2);
    }

    /**
     * Returns <code>true</code> if the float arrays are equal.
     *
     * @param  floatArray1 the first float array
     * @param  floatArray2 the second char array
     * @return <code>true</code> if the float arrays are equal; <code>false
     *         </code>otherwise
     */
    public static boolean equalsSorted(
        float[] floatArray1, float[] floatArray2) {

        floatArray1 = ArrayUtil.clone(floatArray1);

        Arrays.sort(floatArray1);

        floatArray2 = ArrayUtil.clone(floatArray2);

        Arrays.sort(floatArray2);

        return Arrays.equals(floatArray1, floatArray2);
    }

    /**
     * Returns <code>true</code> if the int arrays are equal.
     *
     * @param  intArray1 the first int array
     * @param  intArray2 the second int array
     * @return <code>true</code> if the int arrays are equal; <code>false</code>
     *         otherwise
     */
    public static boolean equalsSorted(int[] intArray1, int[] intArray2) {
        intArray1 = ArrayUtil.clone(intArray1);

        Arrays.sort(intArray1);

        intArray2 = ArrayUtil.clone(intArray2);

        Arrays.sort(intArray2);

        return Arrays.equals(intArray1, intArray2);
    }

    /**
     * Returns <code>true</code> if the long arrays are equal.
     *
     * @param  longArray1 the first long array
     * @param  longArray2 the second long array
     * @return <code>true</code> if the long arrays are equal; <code>false
     *         </code>otherwise
     */
    public static boolean equalsSorted(long[] longArray1, long[] longArray2) {
        longArray1 = ArrayUtil.clone(longArray1);

        Arrays.sort(longArray1);

        longArray2 = ArrayUtil.clone(longArray2);

        Arrays.sort(longArray2);

        return Arrays.equals(longArray1, longArray2);
    }

    /**
     * Returns <code>true</code> if the object arrays are equal.
     *
     * @param  objArray1 the first object array
     * @param  objArray2 the second object array
     * @return <code>true</code> if the object arrays are equal; <code>false
     *         </code>otherwise
     */
    public static boolean equalsSorted(Object[] objArray1, Object[] objArray2) {
        objArray1 = ArrayUtil.clone(objArray1);

        Arrays.sort(objArray1);

        objArray2 = ArrayUtil.clone(objArray2);

        Arrays.sort(objArray2);

        return Arrays.equals(objArray1, objArray2);
    }

    /**
     * Returns <code>true</code> if the short arrays are equal.
     *
     * @param  shortArray1 the first short array
     * @param  shortArray2 the second short array
     * @return <code>true</code> if the short arrays are equal; <code>false
     *         </code>otherwise
     */
    public static boolean equalsSorted(
        short[] shortArray1, short[] shortArray2) {

        shortArray1 = ArrayUtil.clone(shortArray1);

        Arrays.sort(shortArray1);

        shortArray2 = ArrayUtil.clone(shortArray2);

        Arrays.sort(shortArray2);

        return Arrays.equals(shortArray1, shortArray2);
    }

    /**
     * Returns <code>true</code> if the string is an email address. The only
     * requirements are that the string consist of two parts separated by an @
     * symbol, and that it contain no whitespace.
     *
     * @param  address the string to check
     * @return <code>true</code> if the string is an email address;
     *         <code>false</code> otherwise
     */
    public static boolean isAddress(String address) {
        if (isNull(address)) {
            return false;
        }

        String[] tokens = address.split(StringConstants.AT);

        if (tokens.length != 2) {
            return false;
        }

        for (String token : tokens) {
            for (char c : token.toCharArray()) {
                if (Character.isWhitespace(c)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Returns <code>true</code> if the string is an alphanumeric name, meaning
     * it contains nothing but English letters, numbers, and spaces.
     *
     * @param  name the string to check
     * @return <code>true</code> if the string is an Alphanumeric name;
     *         <code>false</code> otherwise
     */
    public static boolean isAlphanumericName(String name) {
        if (isNull(name)) {
            return false;
        }

        String trimmedName = name.trim();

        for (char c : trimmedName.toCharArray()) {
            if (!isChar(c) && !isDigit(c) && !Character.isWhitespace(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns <code>true</code> if the character is in the ASCII character set.
     * This includes characters with integer values between 32 and 126
     * (inclusive).
     *
     * @param  c the character to check
     * @return <code>true</code> if the character is in the ASCII character set;
     *         <code>false</code> otherwise
     */
    public static boolean isAscii(char c) {
        int i = c;

        if ((i >= 32) && (i <= 126)) {
            return true;
        }

        return false;
    }

    public static boolean isBlank(String s) {
        if (s == null) {
            return true;
        }

        if (s.length() == 0) {
            return true;
        }

        return false;
    }

    public static boolean isBoolean(String value) {
        return ArrayUtil.contains(_BOOLEANS, value);
    }

    /**
     * Returns <code>true</code> if the character is an upper or lower case
     * English letter.
     *
     * @param  c the character to check
     * @return <code>true</code> if the character is an upper or lower case
     *         English letter; <code>false</code> otherwise
     */
    public static boolean isChar(char c) {
        int x = c;

        if (((x >= _CHAR_LOWER_CASE_BEGIN) && (x <= _CHAR_LOWER_CASE_END)) ||
            ((x >= _CHAR_UPPER_CASE_BEGIN) && (x <= _CHAR_UPPER_CASE_END))) {

            return true;
        }

        return false;
    }

    /**
     * Returns <code>true</code> if string consists only of upper and lower case
     * English letters.
     *
     * @param  s the string to check
     * @return <code>true</code> if the string consists only of upper and lower
     *         case English letters
     */
    public static boolean isChar(String s) {
        if (isNull(s)) {
            return false;
        }

        for (char c : s.toCharArray()) {
            if (!isChar(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns <code>true</code> if the string contains content. The only
     * requirement is that it contain content that is not whitespace.
     *
     * @param  s the string to check
     * @return <code>true</code> if the string contains content;
     *         <code>false</code> otherwise
     */
    public static boolean isContent(String s) {
        if (isNotNull(
            StringUtil.removeChars(s, '\n', '\t'))) {

            return true;
        }

        return false;
    }

    /**
     * Returns <code>true</code> if the date is valid in the Gregorian calendar.
     *
     * @param  month the month to check
     * @param  day the day to check
     * @param  year the year to check
     * @return <code>true</code> if the date is valid in the Gregorian calendar;
     *         <code>false</code> otherwise
     */
    public static boolean isDate(int month, int day, int year) {
        return isGregorianDate(month, day, year);
    }

    /**
     * Returns <code>true</code> if the character is a digit between 0 and 9
     * (inclusive).
     *
     * @param  c the character to check
     * @return <code>true</code> if the character is a digit between 0 and 9
     *         (inclusive); <code>false</code> otherwise
     */
    public static boolean isDigit(char c) {
        int x = c;

        if ((x >= _DIGIT_BEGIN) && (x <= _DIGIT_END)) {
            return true;
        }

        return false;
    }

    /**
     * Returns <code>true</code> if the string consists of only digits between 0
     * and 9 (inclusive).
     *
     * @param  s the string to check
     * @return <code>true</code> if the string consists of only digits between 0
     *         and 9 (inclusive); <code>false</code> otherwise
     */
    public static boolean isDigit(String s) {
        if (isNull(s)) {
            return false;
        }

        for (char c : s.toCharArray()) {
            if (!isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns <code>true</code> if the string is a valid domain name. See
     * RFC-1034 (section 3), RFC-1123 (section 2.1), and RFC-952 (section B.
     * Lexical grammar).
     *
     * @param  domainName the string to check
     * @return <code>true</code> if the string is a valid domain name;
     *         <code>false</code> otherwise
     */
    public static boolean isDomain(String domainName) {

        // See RFC-1034 (section 3), RFC-1123 (section 2.1), and RFC-952
        // (section B. Lexical grammar)

        if (isNull(domainName)) {
            return false;
        }

        if (domainName.length() > 255) {
            return false;
        }

        if (domainName.startsWith(StringConstants.PERIOD)) {
            return false;
        }

        String[] domainNameArray = StringUtil.split(
            domainName, '.');

        for (String domainNamePart : domainNameArray) {
            char[] domainNamePartCharArray = domainNamePart.toCharArray();

            for (int i = 0; i < domainNamePartCharArray.length; i++) {
                char c = domainNamePartCharArray[i];

                if ((i == 0) && (c == '-')) {
                    return false;
                }

                if ((i == (domainNamePartCharArray.length - 1)) &&
                    (c == '-')) {

                    return false;
                }

                if (!Character.isLetterOrDigit(c) && (c != '-')) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Returns <code>true</code> if the string is a valid email address.
     *
     * @param  emailAddress the string to check
     * @return <code>true</code> if the string is a valid email address;
     *         <code>false</code> otherwise
     */
    public static boolean isEmailAddress(String emailAddress) {
        if (isNull(emailAddress)) {
            return false;
        }

        Matcher matcher = _emailAddressPattern.matcher(emailAddress);

        return matcher.matches();
    }

    /**
     * Returns <code>true</code> if the character is a special character in an
     * email address.
     *
     * @param  c the character to check
     * @return <code>true</code> if the character is a special character in an
     *         email address; <code>false</code> otherwise
     */
    public static boolean isEmailAddressSpecialChar(char c) {

        // LEP-1445

        for (char specialChar : _EMAIL_ADDRESS_SPECIAL_CHAR) {
            if (c == specialChar) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns <code>true</code> if the file extension is valid.
     *
     * @param  fileExtension string to check
     * @return <code>true</code> if the extension is valid; <code>false</code>
     *         otherwise
     */
    public static boolean isFileExtension(String fileExtension) {
        if (isNull(fileExtension) ||
            fileExtension.contains(StringConstants.BACK_SLASH) ||
            fileExtension.contains(StringConstants.NULL_CHAR) ||
            fileExtension.contains(StringConstants.SLASH)) {

            return false;
        }

        return true;
    }

    public static boolean isFileName(String name) {
        if (isNull(name) || name.equals(StringConstants.PERIOD) ||
            name.equals(StringConstants.DOUBLE_PERIOD) ||
            name.contains(StringConstants.BACK_SLASH) ||
            name.contains(StringConstants.NULL_CHAR) ||
            name.contains(StringConstants.SLASH)) {

            return false;
        }

        return true;
    }

    public static boolean isFilePath(String path, boolean parentDirAllowed) {
        if (isNull(path)) {
            return false;
        }

        if (path.contains(StringConstants.NULL_CHAR)) {
            return false;
        }

        if (parentDirAllowed) {
            return true;
        }

        if (path.equals(StringConstants.DOUBLE_PERIOD)) {
            return false;
        }

        String normalizedPath = path.replace(
            '\\', '/');

        if (normalizedPath.startsWith(
            StringConstants.DOUBLE_PERIOD.concat(StringConstants.SLASH))) {

            return false;
        }

        if (normalizedPath.endsWith(
            StringConstants.SLASH.concat(StringConstants.DOUBLE_PERIOD))) {

            return false;
        }

        if (normalizedPath.contains(StringConstants.SLASH
            + StringConstants.DOUBLE_PERIOD
            + StringConstants.SLASH)) {

            return false;
        }

        return true;
    }

    /**
     * Returns <code>true</code> if the date is valid in the Gregorian calendar.
     *
     * @param  month the month (0-based, meaning 0 for January)
     * @param  day the day of the month
     * @param  year the year
     * @return <code>true</code> if the date is valid; <code>false</code>
     *         otherwise
     */
    public static boolean isGregorianDate(int month, int day, int year) {
        if ((month < 0) || (month > 11)) {
            return false;
        }

        int[] months = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if (month == 1) {
            int febMax = 28;

            if ((((year % 4) == 0) && ((year % 100) != 0)) ||
                ((year % 400) == 0)) {

                febMax = 29;
            }

            if ((day < 1) || (day > febMax)) {
                return false;
            }
        }
        else if ((day < 1) || (day > months[month])) {
            return false;
        }

        return true;
    }

    /**
     * Returns <code>true</code> if the string is a hexidecimal number. At
     * present the only requirement is that the string is not <code>null</code>;
     * it does not actually check the format of the string.
     *
     * @param  s the string to check
     * @return <code>true</code> if the string is a hexidecimal number;
     *         <code>false</code> otherwise
     * @see    #isNull(String)
     */
    public static boolean isHex(String s) {
        if (isNull(s)) {
            return false;
        }

        return true;
    }

    /**
     * Returns <code>true</code> if the string is a valid host name.
     *
     * @param  name the string to check
     * @return <code>true</code> if the string is a valid host name;
     *         <code>false</code> otherwise
     */
    public static boolean isHostName(String name) {
        if (isNull(name)) {
            return false;
        }

        char[] nameCharArray = name.toCharArray();

        if ((nameCharArray[0] == '-') ||
            (nameCharArray[0] == '.') ||
            (nameCharArray[nameCharArray.length - 1] == '-')) {

            return false;
        }

        for (char c : nameCharArray) {
            if (!isChar(c) && !isDigit(c) && (c != ']') &&
                (c != ':') && (c != '-') &&
                (c != '[') && (c != '.')) {

                return false;
            }
        }

        return true;
    }

    /**
     * Returns <code>true</code> if the string is an HTML document. The only
     * requirement is that it contain the opening and closing html tags.
     *
     * @param  s the string to check
     * @return <code>true</code> if the string is an HTML document;
     *         <code>false</code> otherwise
     */
    public static boolean isHTML(String s) {
        if (isNull(s)) {
            return false;
        }

        if ((s.contains("<html>") || s.contains("<HTML>")) &&
            (s.contains("</html>") || s.contains("</HTML>"))) {

            return true;
        }

        return false;
    }

    /**
     * Returns <code>true</code> if the date is valid in the Julian calendar.
     *
     * @param  month the month (0-based, meaning 0 for January)
     * @param  day the day of the month
     * @param  year the year
     * @return <code>true</code> if the date is valid in the Julian calendar;
     *         <code>false</code> otherwise
     */
    public static boolean isJulianDate(int month, int day, int year) {
        if ((month < 0) || (month > 11)) {
            return false;
        }

        int[] months = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if (month == 1) {
            int febMax = 28;

            if ((year % 4) == 0) {
                febMax = 29;
            }

            if ((day < 1) || (day > febMax)) {
                return false;
            }
        }
        else if ((day < 1) || (day > months[month])) {
            return false;
        }

        return true;
    }

    /**
     * Returns <code>true</code> if the string contains a valid number according
     * to the Luhn algorithm, commonly used to validate credit card numbers.
     *
     * @param  number the string to check
     * @return <code>true</code> if the string contains a valid number according
     *         to the Luhn algorithm; <code>false</code> otherwise
     */
    public static boolean isLUHN(String number) {
        if (number == null) {
            return false;
        }

        int sum = 0;

        int length = number.length();

        for (int i = 0; i < length; i++) {
            int x = number.charAt(length - 1 - i) - '0';

            if ((x > 9) || (x < 0)) {
                return false;
            }

            if ((i % 2) == 1) {
                x *= 2;

                if (x > 9) {
                    x -= 9;
                }
            }

            sum += x;
        }

        if ((sum % 10) == 0) {
            return true;
        }

        return false;
    }

    /**
     * Returns <code>true</code> if the string is a name, meaning it contains
     * nothing but English letters and spaces.
     *
     * @param  name the string to check
     * @return <code>true</code> if the string is a name; <code>false</code>
     *         otherwise
     */
    public static boolean isName(String name) {
        if (isNull(name)) {
            return false;
        }

        String trimmedName = name.trim();

        for (char c : trimmedName.toCharArray()) {
            if (!isChar(c) && !Character.isWhitespace(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns <code>true</code> if the long number object is not
     * <code>null</code>, meaning it is neither a <code>null</code> reference or
     * zero.
     *
     * @param  l the long number object to check
     * @return <code>true</code> if the long number object is not
     *         <code>null</code>; <code>false</code> otherwise
     */
    public static boolean isNotNull(Long l) {
        return !isNull(l);
    }

    /**
     * Returns <code>true</code> if the object is not <code>null</code>, using
     * the rules from {@link #isNotNull(Long)} or {@link #isNotNull(String)} if
     * the object is one of these types.
     *
     * @param  obj the object to check
     * @return <code>true</code> if the object is not <code>null</code>;
     *         <code>false</code> otherwise
     */
    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    /**
     * Returns <code>true</code> if the string is not <code>null</code>, meaning
     * it is not a <code>null</code> reference, an empty string, whitespace, or
     * the string "<code>null</code>", with or without leading or trailing
     * whitespace.
     *
     * @param  s the string to check
     * @return <code>true</code> if the string is not <code>null</code>;
     *         <code>false</code> otherwise
     */
    public static boolean isNotNull(String s) {
        return !isNull(s);
    }

    /**
     * Returns <code>true</code> if the long number object is <code>null</code>,
     * meaning it is either a <code>null</code> reference or zero.
     *
     * @param  l the long number object to check
     * @return <code>true</code> if the long number object is <code>null</code>;
     *         <code>false</code> otherwise
     */
    public static boolean isNull(Long l) {
        if ((l == null) || (l.longValue() == 0)) {
            return true;
        }

        return false;
    }

    /**
     * Returns <code>true</code> if the object is <code>null</code>, using the
     * rules from {@link #isNull(Long)} or {@link #isNull(String)} if the object
     * is one of these types.
     *
     * @param  obj the object to check
     * @return <code>true</code> if the object is <code>null</code>;
     *         <code>false</code> otherwise
     */
    public static boolean isNull(Object obj) {
        if (obj instanceof Long) {
            return isNull((Long)obj);
        }
        else if (obj instanceof String) {
            return isNull((String)obj);
        }
        else if (obj == null) {
            return true;
        }

        return false;
    }

    /**
     * Returns <code>true</code> if the string is <code>null</code>, meaning it
     * is a <code>null</code> reference, an empty string, whitespace, or the
     * string "<code>null</code>", with or without leading or trailing
     * whitespace.
     *
     * @param  s the string to check
     * @return <code>true</code> if the string is <code>null</code>;
     *         <code>false</code> otherwise
     */
    public static boolean isNull(String s) {
        if (s == null) {
            return true;
        }

        int counter = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == ' ') {
                continue;
            }
            else if (counter > 3) {
                return false;
            }

            if (counter == 0) {
                if (c != 'n') {
                    return false;
                }
            }
            else if (counter == 1) {
                if (c != 'u') {
                    return false;
                }
            }
            else if ((counter == 2) || (counter == 3)) {
                if (c != 'l') {
                    return false;
                }
            }

            counter++;
        }

        if ((counter == 0) || (counter == 4)) {
            return true;
        }

        return false;
    }

    /**
     * Returns <code>true</code> if the string is a decimal integer number,
     * meaning it contains nothing but decimal digits.
     *
     * @param  number the string to check
     * @return <code>true</code> if the string is a decimal integer number;
     *         <code>false</code> otherwise
     */
    public static boolean isNumber(String number) {
        if (isNull(number)) {
            return false;
        }

        for (char c : number.toCharArray()) {
            if (!isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns <code>true</code> if the string is a valid password, meaning it
     * is at least four characters long and contains only letters and decimal
     * digits.
     *
     * @param  password the string to check
     * @return <code>true</code> if the string is a valid password;
     *         <code>false</code> otherwise
     */
    public static boolean isPassword(String password) {
        if (isNull(password)) {
            return false;
        }

        if (password.length() < 4) {
            return false;
        }

        for (char c : password.toCharArray()) {
            if (!isChar(c) && !isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns <code>true</code> if the string is a valid phone number. The only
     * requirement is that there are decimal digits in the string; length and
     * format are not checked.
     *
     * @param  phoneNumber the string to check
     * @return <code>true</code> if the string is a valid phone number;
     *         <code>false</code> otherwise
     */
    public static boolean isPhoneNumber(String phoneNumber) {
        return isNumber(StringUtil.extractDigits(phoneNumber));
    }

    public static boolean isUri(String uri) {
        if (isNotNull(uri)) {
            try {
                new URI(uri);

                return true;
            }
            catch (URISyntaxException urise) {
            }
        }

        return false;
    }

    /**
     * Returns <code>true</code> if the string is a valid URL based on the rules
     * in {@link URL}.
     *
     * @param  url the string to check
     * @return <code>true</code> if the string is a valid URL;
     *         <code>false</code> otherwise
     */
    public static boolean isUrl(String url) {
        return isUrl(url, false);
    }

    /**
     * Returns <code>true</code> if the string is a valid URL based on the rules
     * in {@link URL}. This method can also validate root relative URLs.
     *
     * @param  url the string to check
     * @param  acceptRootRelative whether a root relative URL should be accepted
     * @return <code>true</code> if the string is a valid URL;
     *         <code>false</code> otherwise
     */
    public static boolean isUrl(String url, boolean acceptRootRelative) {
        if (isNotNull(url)) {
            if (acceptRootRelative && (url.charAt(0) == '/')) {
                return true;
            }

            if (url.indexOf(':') == -1) {
                return false;
            }

            try {
                new URL(url);

                return true;
            }
            catch (MalformedURLException murle) {
            }
        }

        return false;
    }

    /**
     * Returns <code>true</code> if the string is a valid variable name in Java.
     *
     * @param  variableName the string to check
     * @return <code>true</code> if the string is a valid variable name in Java;
     *         <code>false</code> otherwise
     */
    public static boolean isVariableName(String variableName) {
        if (isNull(variableName) ||
            ArrayUtil.contains(_JAVA_KEYWORDS, variableName)) {

            return false;
        }

        Matcher matcher = _variableNamePattern.matcher(variableName);

        if (matcher.matches()) {
            return true;
        }

        return false;
    }

    /**
     * Returns <code>true</code> if the string is a valid variable term, meaning
     * it begins with "[$" and ends with "$]".
     *
     * @param  s the string to check
     * @return <code>true</code> if the string is a valid variable term;
     *         <code>false</code> otherwise
     */
    public static boolean isVariableTerm(String s) {
        if (s.startsWith(_VARIABLE_TERM_BEGIN) &&
            s.endsWith(_VARIABLE_TERM_END)) {

            return true;
        }

        return false;
    }

    /**
     * Returns <code>true</code> if the character is whitespace, meaning it is
     * either the <code>null</code> character '0' or whitespace according to
     * {@link Character#isWhitespace(char)}.
     *
     * @param  c the character to check
     * @return <code>true</code> if the character is whitespace;
     *         <code>false</code> otherwise
     */
    public static boolean isWhitespace(char c) {
        int i = c;

        if ((i == 0) || Character.isWhitespace(c)) {
            return true;
        }

        return false;
    }

    /**
     * Returns <code>true</code> if the string is an XML document. The only
     * requirement is that it contain either the xml start tag "<?xml" or the
     * empty document tag "<root />".
     *
     * @param  s the string to check
     * @return <code>true</code> if the string is an XML document;
     *         <code>false</code> otherwise
     */
    public static boolean isXml(String s) {
        if (isNull(s)) {
            return false;
        }
        else if (s.startsWith(_XML_BEGIN) || s.startsWith(_XML_EMPTY)) {
            return true;
        }

        return false;
    }

    private static final String[] _BOOLEANS = {"false", "on", "off", "true"};

    private static final int _CHAR_LOWER_CASE_BEGIN = 97;

    private static final int _CHAR_LOWER_CASE_END = 122;

    private static final int _CHAR_UPPER_CASE_BEGIN = 65;

    private static final int _CHAR_UPPER_CASE_END = 90;

    private static final int _DIGIT_BEGIN = 48;

    private static final int _DIGIT_END = 57;

    private static final char[] _EMAIL_ADDRESS_SPECIAL_CHAR = {
        '.', '!', '#', '$', '%', '&', '\'', '*', '+', '-', '/', '=', '?', '^',
        '_', '`', '{', '|', '}', '~'
    };

    private static final String[] _JAVA_KEYWORDS = {
        "abstract", "assert", "boolean", "break", "byte", "case", "catch",
        "char", "class", "const", "continue", "default", "do", "double", "else",
        "enum", "extends", "false", "final", "finally", "float", "for", "goto",
        "if", "implements", "import", "instanceof", "int", "interface", "long",
        "native", "new", "null", "package", "private", "protected", "public",
        "return", "short", "static", "strictfp", "super", "switch",
        "synchronized", "this", "throw", "throws", "transient", "true", "try",
        "void", "volatile", "while"
    };

    private static final String _VARIABLE_TERM_BEGIN = "[$";

    private static final String _VARIABLE_TERM_END = "$]";

    private static final String _XML_BEGIN = "<?xml";

    private static final String _XML_EMPTY = "<root />";

    private static final Pattern _emailAddressPattern = Pattern.compile(
        "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@" +
            "(?:\\w(?:[\\w-]*\\w)?\\.)*\\w(?:[\\w-]*\\w)?");
    private static final Pattern _variableNamePattern = Pattern.compile(
        "[_a-zA-Z]+[_a-zA-Z0-9]*");

    public void validateDirectoryName(String directoryName)
        throws FolderNameException {

        if (!isValidName(directoryName)) {
            throw new FolderNameException(
                "Invalid folder name " + directoryName);
        }
    }

    public static boolean isValidName(String name) {
        if (Validator.isNull(name)) {
            return false;
        }

        for (String blacklistChar : StringConstants.DL_CHAR_BLACKLIST) {
            if (name.contains(blacklistChar)) {
                return false;
            }
        }

        for (String blacklistLastChar : StringConstants.DL_CHAR_LAST_BLACKLIST) {

            if (name.endsWith(blacklistLastChar)) {
                return false;
            }
        }

        String nameWithoutExtension = FileUtil.stripExtension(name);

        for (String blacklistName : StringConstants.DL_NAME_BLACKLIST) {
            if (StringUtil.equalsIgnoreCase(
                nameWithoutExtension, blacklistName)) {

                return false;
            }
        }

        return true;
    }

    public static void validateFileName(String fileName) throws FileNameException {
        if (!isValidName(fileName)) {
            throw new FileNameException("Invalid file name " + fileName);
        }
    }

    public static void validateFileExtension(String fileName) throws FileExtensionException {
        boolean validFileExtension = false;

        String[] fileExtensions = getfileExtensions();

        for (String fileExtension : fileExtensions) {
            if (StringConstants.STAR.equals(fileExtension) ||
                StringUtil.endsWith(fileName, fileExtension)) {

                validFileExtension = true;

                break;
            }
        }

        if (!validFileExtension) {
            throw new FileExtensionException(
                "Invalid file extension for " + fileName);
        }
    }

    public static void validateFileSize(String fileName, byte[] bytes) throws FileSizeException {
        if (bytes == null) {
            throw new FileSizeException("File size is zero for " + fileName);
        }

        validateFileSize(fileName, bytes.length);
    }

    public static void validateFileSize(String fileName, File file) throws FileSizeException {
        if (file == null) {
            throw new FileSizeException("File is null for " + fileName);
        }
        validateFileSize(fileName, file.length());
    }

    public static void validateFileSize(String fileName, InputStream is) throws FileSizeException {
        try {
            if (is == null) {
                throw new FileSizeException(
                    "Input stream is null for " + fileName);
            }

            validateFileSize(fileName, is.available());
        }
        catch (IOException ioe) {
            throw new FileSizeException(ioe);
        }
    }


    public static void validateFileSize(String fileName, long size)
        throws FileSizeException {

        long maxSize = getMaxAllowableSize();

        if ((maxSize > 0) && (size > maxSize)) {
            throw new FileSizeException(
                    size + " exceeds the maximum permitted size of " + maxSize +
                    " for file " + fileName);
        }
    }

    public static long getMaxAllowableSize() {
        return 0;
    }
    public static String getAllowedfileExtensions(){
        return StringConstants.STAR;
    }
    public static String[] getfileExtensions(){
        return getAllowedfileExtensions().split(StringConstants.COMMA);
    }
}
