package com.transaction.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NameIdPojo {
    private Long id;
    private String name;

    public NameIdPojo(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
