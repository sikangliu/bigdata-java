package com.lsk.juc;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CountryEnums {
    ONE(1, "韩"), TWO(2, "魏"), THREE(3, "赵"), FOUR(4, "齐"), FIVE(5, "楚"), SIX(6, "燕");

    @Getter
    private Integer retCode;
    @Getter
    private String retMessage;

    public static CountryEnums forEachCountryEnums(Integer index) {
        for (CountryEnums element : values()) {
            if (element.getRetCode().equals(index))
            {
                return element;
            }
        }
        return null;
    }
}