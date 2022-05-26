package com.andrew2dos.simplebank;


import com.andrew2dos.simplebank.entity.Balance;
import lombok.AllArgsConstructor;
import org.apache.catalina.Store;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

// Слой для взаимодействия с внешним миром
@Service
@AllArgsConstructor
public class BankService {


    private BalanceRepository balanceRepository;

//    public Balance getBalance(Long id) {
//        Balance balance = null;
//        Optional<Balance> optional = balanceRepository.findById(id);
//        if(optional.isPresent()){
//            balance = optional.get();
//        }
//        return balance;
//    }

    public BigDecimal getBalance(Long accountId) {
        BigDecimal amount = balanceRepository.getOne(accountId).getAmount();
        if (amount == null) {
            throw new IllegalArgumentException();
        }
        return amount;
    }

//    public void saveBalance(Balance balance) {
//        balanceRepository.save(balance);
//    }

    public BigDecimal addMoney(Long id, BigDecimal amount) {
        BigDecimal updatedBalance = getBalance(id).add(amount);

        Balance balance = balanceRepository.getById(id);
        balance.setAmount(updatedBalance);
        balanceRepository.save(balance);

        return updatedBalance;

    }

//    public void makeTransfer(TransferBalance transferBalance) {
//        BigDecimal fromBalance = balanceEntity.getBalanceForId(transferBalance.getFrom());
//        BigDecimal toBalance = balanceEntity.getBalanceForId(transferBalance.getTo());
//        if(fromBalance == null || toBalance == null){
//            throw new IllegalArgumentException("no ids");
//        }
//        if(transferBalance.getAmount().compareTo(fromBalance) > 0) {
//            throw new IllegalArgumentException("not enough money");
//        }
//
//        BigDecimal updateFromBalance = fromBalance.subtract(transferBalance.getAmount());
//        BigDecimal updateToBalance = toBalance.add(transferBalance.getAmount());
//        balanceEntity.save(transferBalance.getFrom(), updateFromBalance);
//        balanceEntity.save(transferBalance.getTo(), updateToBalance);
//    }
}
