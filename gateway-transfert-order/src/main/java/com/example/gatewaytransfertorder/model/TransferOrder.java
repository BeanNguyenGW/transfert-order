package com.example.gatewaytransfertorder.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString(includeFieldNames = true)
public class TransferOrder {
    private String orderId;
    private String sourceUrl;
}
