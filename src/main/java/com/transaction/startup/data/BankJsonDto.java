package com.transaction.startup.data;

import com.transaction.enums.BankTypeConstant;
import lombok.Data;

@Data
public class BankJsonDto {
    private String name;
    private String code;
    private BankTypeConstant bankType;
    private String countryAlpha2;
    private String fwId;
    private String fwCode;
}
