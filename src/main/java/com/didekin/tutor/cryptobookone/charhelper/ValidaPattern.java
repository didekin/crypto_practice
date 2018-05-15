package com.didekin.tutor.cryptobookone.charhelper;

import java.util.regex.Pattern;

/**
 * User: pedro@didekin
 * Date: 16/08/17
 * Time: 11:08
 */
public enum ValidaPattern {

    EMAIL("[\\w\\._\\-]{1,48}@[\\w\\-_]{1,40}\\.[\\w&&[^0-9]]{1,10}"),
    PASSWORD("[0-9a-zA-Z_ñÑáéíóúüÜ]{6,13}"),
    ALIAS("[0-9a-zA-Z_ñÑáéíóúüÜ ]{6,30}"),
    LINE_BREAK("\n"),
    ;

    private final Pattern pattern;
    private final String regexp;

    ValidaPattern(String patternString) {
        this.pattern = Pattern.compile(patternString, 66);
        this.regexp = patternString;
    }

    public boolean isPatternOk(String fieldToCheck) {
        return this.pattern.matcher(fieldToCheck).matches();
    }

    public String getRegexp() {
        return this.regexp;
    }
}
