package com.example.gatewaytransfertorder.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Getter
@ToString(includeFieldNames = true)
public class TransferOrderRequest {
    @NotNull
    private Long sourceId;
    @NotNull
    private Long targetId;
    @NotEmpty
    private String orderId;
    @NotNull
    private String targetUrl;
}
