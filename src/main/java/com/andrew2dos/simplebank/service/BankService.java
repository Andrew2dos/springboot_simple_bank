package com.andrew2dos.simplebank.service;

import com.andrew2dos.simplebank.entity.Balance;
import com.andrew2dos.simplebank.model.TransferRequest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface BankService {
    @Transactional(readOnly = true)
    List<Balance> getAllAccounts();

    @Transactional(readOnly = true)
    BigDecimal getBalance(Long accountId);

    Balance createAccount(Balance balance);

    Balance saveBalance(Long id, BigDecimal amount);

    BigDecimal addMoney(Long id, BigDecimal amount);

    void makeTransfer(TransferRequest transferRequest);

    void deleteById(Long id);

    BigDecimal resumeMoney(Long id, BigDecimal amount);
}
