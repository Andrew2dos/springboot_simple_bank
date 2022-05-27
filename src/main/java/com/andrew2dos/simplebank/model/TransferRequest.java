package com.andrew2dos.simplebank.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {

    private Long from;
    private Long to;
    private BigDecimal amount;
}
