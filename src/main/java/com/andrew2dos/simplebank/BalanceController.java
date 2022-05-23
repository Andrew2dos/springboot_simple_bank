package com.andrew2dos.simplebank;

import com.andrew2dos.simplebank.entity.Balance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Slf4j // Lombok сам поднимает логгер и подключает экземпляр логгера
@RestController("/balance")
//@AllArgsConstructor используется вместо @Autowired и создает сам конструктор
public class BalanceController {

    // Подключение слоя чтоб делать что-то на этом слое
    @Autowired // чтоб заинжектился BankService
    private BankService bankService;

    @GetMapping("/{accountId}")
    public int getBalance(@PathVariable int accountId){
        Balance balance = bankService.getBalance(accountId);
        return balance.getAmount();
    }

    // Получить текущий счет
//    @GetMapping("/{accountId}")
//    public BigDecimal getBalance(@PathVariable Long accountId){
//        return BigDecimal.valueOf(48); //bankService.getBalance(accountId);
//    }

//    // Добавить деньги на счет указанный в TransferBalance
//    @PostMapping ("/add")
//    public BigDecimal addMoney(@RequestBody TransferBalance transferBalance){
//        return bankService.addMoney(transferBalance.getTo(), transferBalance.getAmount());
//    }
//
//    // Перевести деньги
//    @PostMapping ("/transfer")
//    public void transfer(@RequestBody TransferBalance transferBalance){
//        bankService.makeTransfer(transferBalance);
//    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handle(IllegalArgumentException e) {
        log.error(e.getMessage());
        return "ОШИБКА.ЧТО-ТО ПОШЛО НЕ ТАК";
    }
}
