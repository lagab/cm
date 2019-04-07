package com.lagab.cmanager.web.rest.util;

/**
 * @author GABRIEL
 * @since 05/04/2019.
 */
public class StringConstants {
    public static final String BLANK = "";
    public static final String SLASH = "/";
    public static final String COMMA = ",";
    public static final String STAR = "*";
    public static final String AT = "@";
    public static final String PERIOD = ".";
    public static final String BACK_SLASH = "\\";
    public static final String NULL_CHAR = "\u0000";
    public static final String DOUBLE_PERIOD = "..";
    public static final String SPACE = " ";
    public static final String NEW_LINE = "\n";
    public static final String UTF8 = "UTF-8";
    public static final String DL_CHAR_BLACK_LIST = "\\\\,//,:,*,?,\",<,>,|,[,],../,/..";
    public static final String[] DL_CHAR_BLACKLIST = DL_CHAR_BLACK_LIST.split(COMMA);
    public static final String[] DL_CHAR_LAST_BLACKLIST = "".split(COMMA);
    public static final String[] DL_NAME_BLACKLIST = "".split(COMMA);
}
