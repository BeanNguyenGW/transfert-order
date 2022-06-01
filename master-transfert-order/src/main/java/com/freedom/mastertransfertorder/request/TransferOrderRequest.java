package com.freedom.mastertransfertorder.request;

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
@RequiredArgsConstructor
@Getter
@ToString(includeFieldNames = true)
public class TransferOrderRequest {
    @NotNull
    private Long sourceId;
    @NotNull
    private Long targetId;
    @NotEmpty
    private Set<String> transferOrderIds = new HashSet<>();
}
