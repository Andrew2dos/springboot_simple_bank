package com.andrew2dos.simplebank;


import com.andrew2dos.simplebank.entity.Balance;
import com.andrew2dos.simplebank.model.TransferRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

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
        BigDecimal amount = balanceRepository.getOne(accountId).getAmount(); // получаем объект из БД по id и берем у него Счет
        if (amount == null) {
            throw new IllegalArgumentException();
        }
        return amount;
    }

//    public void saveBalance(Balance balance) {
//        balanceRepository.save(balance);
//    }

    public BigDecimal addMoney(Long id, BigDecimal amount) {
        BigDecimal updatedBalance = getBalance(id).add(amount); //получаем объект из БД по id и берем у него Счет к нему добовлаем сумму из перевода

        Balance balance = balanceRepository.getById(id); // берем объект из БД и помещаем его в balance
        balance.setAmount(updatedBalance); // присваеваем новую сумму на счет
        balanceRepository.save(balance); // сохраняем объект

        return updatedBalance;

    }

        public void makeTransfer(TransferRequest transferRequest) {
            BigDecimal fromBalance = getBalance(transferRequest.getFrom()); // получаем объект из БД по id и берем у него Счет
            BigDecimal toBalance = getBalance(transferRequest.getTo());
            if (fromBalance == null || toBalance == null) throw new IllegalArgumentException("no ids");
            if (transferRequest.getAmount().compareTo(fromBalance) > 0) throw new IllegalArgumentException("no money");

            BigDecimal updatedFromBalance = fromBalance.subtract(transferRequest.getAmount());
            BigDecimal updatedToBalance = toBalance.add(transferRequest.getAmount());

            Balance balanceFrom = balanceRepository.getById(transferRequest.getFrom()); // берем объект F из БД и помещаем его в balance
            balanceFrom.setAmount(updatedFromBalance); // присваеваем новую сумму на счет
            balanceRepository.save(balanceFrom); // сохраняем объект

            Balance balanceTo = balanceRepository.getById(transferRequest.getTo()); // берем объект T из БД и помещаем его в balance
            balanceTo.setAmount(updatedToBalance); // присваеваем новую сумму на счет
            balanceRepository.save(balanceTo); // сохраняем объект
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
