package com.transaction.startup.data;

import lombok.Data;
@Data
public class CurrencyDto {
    private String countryName;
    private String countryCode;
    private String iso4217Code;
    private String name;
    private String symbol;
    private String majorUnitName;
    private String minorUnitName;
    private String currencyType;

}
