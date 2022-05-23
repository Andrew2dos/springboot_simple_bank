package com.andrew2dos.simplebank;


import com.andrew2dos.simplebank.entity.Balance;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

// Слой для взаимодействия с внешним миром
@Service
@AllArgsConstructor
public class BankService {

    private final BalanceRepository balanceRepository;

    public Balance getBalance(int id) {
        Balance balance = null;
        Optional<Balance> optional = balanceRepository.findById(id);
        if(optional.isPresent()){
            balance = optional.get();
        }
        return balance;
    }

//    public BigDecimal getBalance(Long accountId) {
//        Balance balance = balanceRepository.getOne(accountId);
//        BigDecimal decimal = balance.getAmount();
//        if (balance == null) {
//            throw new IllegalArgumentException();
//        }
//        return decimal;
//    }

//    public BigDecimal getBalance(Long accountId) {
//        BigDecimal balance = balanceEntity.getBalanceForId(accountId);
//        if(balance == null){
//            throw new IllegalArgumentException();
//        }
//        return balance;
//    }

//    public BigDecimal addMoney(Long to, BigDecimal amount) {
//        BigDecimal currentBalance = balanceEntity.getBalanceForId(to);
//        if(currentBalance == null){
//            balanceEntity.save(to, amount);
//            return amount;
//        } else {
//            BigDecimal updatedBalance = currentBalance.add(amount);
//            balanceEntity.save(to, updatedBalance);
//            return updatedBalance;
//        }
//    }
//
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
