package com.transaction.pojo;

import lombok.Data;

@Data
public class NameCodePojo {
    private String code;
    private String name;

    public NameCodePojo(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
