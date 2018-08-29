package com.newframe.enums.user;

import org.apache.commons.lang3.StringUtils;

/**
 * @author WangBin
 */
public enum PatternEnum {

    mobile("^1[\\d]{10}"),
    ;
    private String pattern;

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    PatternEnum(String pattern) {
        this.pattern = pattern;
    }

    public static boolean checkPattern(CharSequence cs, PatternEnum pattern){
        if(StringUtils.isEmpty(cs) || pattern == null){
            return false;
        }
        return cs.toString().matches(pattern.getPattern());
    }
}
