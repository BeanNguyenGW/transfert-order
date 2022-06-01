package com.freedom.mastertransfertorder.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TransferOrder {
    private String orderId;
    private Long sourceId;
    private Long targetId;
    private String targetUrl;
}
