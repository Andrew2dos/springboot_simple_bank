package com.andrew2dos.simplebank.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TransferRequest {

    @NotNull(message = "Cannot be Null")
    private Long from;
    @NotNull(message = "Cannot be Null")
    private Long to;
    @NotNull(message = "Amount cannot be Null")
    private BigDecimal amount;
}
