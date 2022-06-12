package com.andrew2dos.simplebank.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "storage", schema = "public", catalog = "shopping_list_db")
@Data
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotEmpty(message = "Id's cannot be Null")
    private Long id;

    @NotNull(message = "Amount's cannot be empty.")
    private BigDecimal amount;
}
