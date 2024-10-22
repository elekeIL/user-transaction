package com.transaction.dto.pubSub;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PubSubData<T> {
    private T data;
    private Summary summary;
}
