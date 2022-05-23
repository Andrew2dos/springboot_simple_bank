package com.andrew2dos.simplebank.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "storage", schema = "public", catalog = "shopping_list_db")
@Data
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int amount;
}
