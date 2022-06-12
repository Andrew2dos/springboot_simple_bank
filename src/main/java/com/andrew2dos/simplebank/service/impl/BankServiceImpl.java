package com.andrew2dos.simplebank.service.impl;

import com.andrew2dos.simplebank.entity.Balance;
import com.andrew2dos.simplebank.exception.AccountNotFoundException;
import com.andrew2dos.simplebank.model.TransferRequest;
import com.andrew2dos.simplebank.repository.BalanceRepository;
import com.andrew2dos.simplebank.service.BankService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class BankServiceImpl implements BankService {

    private BalanceRepository balanceRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Balance> getAllAccounts() {
        return balanceRepository.findAllByOrderByIdAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getBalance(Long accountId) {
        return balanceRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(String.format("Account with id %d is not found", accountId)))
                .getAmount();
    }

    @Override
    public Balance createAccount(Balance balance) {
//        if (balanceRepository.findById(balance.getId())
//                .isPresent()) {
//            throw new IllegalStateException(String.format("Account with id %d already exists", balance.getId()));
//        }
        return balanceRepository.save(balance);
    }

    @Override
    public Balance saveBalance(Long id, BigDecimal amount) {
        Balance balance = balanceRepository.getById(id);
        balance.setAmount(amount);
        return balanceRepository.save(balance);
    }


    @Override
    public BigDecimal addMoney(Long id, BigDecimal amount) {
        validateAmount(amount);
        BigDecimal updatedBalance = getBalance(id).add(amount);
        saveBalance(id, updatedBalance);
        return updatedBalance;
    }

    @Override
    public BigDecimal resumeMoney(Long id, BigDecimal amount) {
        validateAmount(amount);
        BigDecimal updatedBalance = getBalance(id).subtract(amount);
        saveBalance(id, updatedBalance);
        return updatedBalance;
    }

    @Override
    public void makeTransfer(TransferRequest transferRequest) {
        BigDecimal fromBalance = getBalance(transferRequest.getFrom());
        BigDecimal toBalance = getBalance(transferRequest.getTo());
        validateAmount(transferRequest.getAmount());
        if (transferRequest.getAmount().compareTo(fromBalance) > 0) {
            throw new IllegalArgumentException("Not enough money");
        }

        BigDecimal updatedFromBalance = fromBalance.subtract(transferRequest.getAmount());
        BigDecimal updatedToBalance = toBalance.add(transferRequest.getAmount());

        saveBalance(transferRequest.getFrom(), updatedFromBalance);
        saveBalance(transferRequest.getTo(), updatedToBalance);
    }

    @Override
    public void deleteById(Long id) {
        balanceRepository.deleteById(id);
    }

    private void validateAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
    }
}
