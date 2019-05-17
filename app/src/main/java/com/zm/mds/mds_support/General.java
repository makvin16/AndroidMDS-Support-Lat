package com.zm.mds.mds_support;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by moska on 09.10.2017.
 */

public class General {
    public static final int MAX_LENGHT_PASSWORD = 4;
    public static final String EMAIL_SUPPORT = "mailto:info@ap-cards.com";
    public static final String FILE_EXCEL = "Report.xls";
    public static final String ITEM_NAME = "Клиент";
    public static final String ITEM_PERCENTAGE = "Скидка";
    public static final String ITEM_SUM = "Сумма";
    public static final String ITEM_PAYMENT = "Оплата";
    public static final String ITEM_DATE = "Дата";
    public static final String ITEM_TIME = "Время";
    public static final String ITEM_NUMBER_CHECK = "Номер чека";
    public static final String ITEM_DATE_OF_BIRTH = "Дата рождения";
    public static final String ITEM_PHONE = "Телефон";
    public static final String ITEM_EMAIL = "E-mail";



    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }
}
