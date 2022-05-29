package com.andrew2dos.simplebank;

import com.andrew2dos.simplebank.entity.Balance;
import com.andrew2dos.simplebank.model.TransferRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class BankService {

    private BalanceRepository balanceRepository;

    public BigDecimal getBalance(Long accountId) {
        BigDecimal amount = balanceRepository.getOne(accountId).getAmount();
        if (amount == null) {
            throw new IllegalArgumentException();
        }
        return amount;
    }

    public void createAccount(Balance balance) {
        balanceRepository.save(balance);
    }

    public void saveBalance(Long id, BigDecimal amount) {
        Balance balance = balanceRepository.getById(id);
        balance.setAmount(amount);
        balanceRepository.save(balance);
    }

    public BigDecimal addMoney(Long id, BigDecimal amount) {
        BigDecimal updatedBalance = getBalance(id).add(amount);
        saveBalance(id, updatedBalance);
        return updatedBalance;
    }

    public void makeTransfer(TransferRequest transferRequest) {
        BigDecimal fromBalance = getBalance(transferRequest.getFrom());
        BigDecimal toBalance = getBalance(transferRequest.getTo());
        if (fromBalance == null || toBalance == null) throw new IllegalArgumentException("no ids");
        if (transferRequest.getAmount().compareTo(fromBalance) > 0) throw new IllegalArgumentException("no money");

        BigDecimal updatedFromBalance = fromBalance.subtract(transferRequest.getAmount());
        BigDecimal updatedToBalance = toBalance.add(transferRequest.getAmount());

        saveBalance(transferRequest.getFrom(), updatedFromBalance);
        saveBalance(transferRequest.getTo(), updatedToBalance);
    }
}
