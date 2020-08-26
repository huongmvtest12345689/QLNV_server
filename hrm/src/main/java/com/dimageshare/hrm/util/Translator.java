package com.dimageshare.hrm.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Translator {
    private static ReloadableResourceBundleMessageSource messageSource;

    @Autowired
    Translator(ReloadableResourceBundleMessageSource messageSource) {
        Translator.messageSource = messageSource;
    }

    public static String translate(String msgCode) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(msgCode, null, locale);
    }

    public static String translate(String msgCode, String... data) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(msgCode, data, locale);
    }

    public static String translate(String msgCode, Locale locale) {
        return messageSource.getMessage(msgCode, null, locale);
    }

    public static String translate(String msgCode, Locale locale, String... data) {
        return messageSource.getMessage(msgCode, data, locale);
    }

}
