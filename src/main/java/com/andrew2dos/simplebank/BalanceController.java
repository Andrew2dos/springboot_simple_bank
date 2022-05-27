package com.andrew2dos.simplebank;

import com.andrew2dos.simplebank.entity.Balance;
import com.andrew2dos.simplebank.model.TransferRequest;
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

    // Получить текущий счет
    @GetMapping("/{accountId}")
    public BigDecimal getBalance(@PathVariable Long accountId) {
        BigDecimal balance = bankService.getBalance(accountId);
        return balance;
    }

//    // Добавить новый счет
//    @PostMapping("/add")
//    public Balance addNewAccount(@RequestBody Balance balance){
//        bankService.saveBalance(balance);
//        return balance;
//    }

    // Добавить деньги на счет указанный в Balance
    @PostMapping("/add")
    public BigDecimal addMoney(@RequestBody TransferRequest request) {
        return bankService.addMoney(request.getTo(), request.getAmount());
    }

        // Перевести деньги
    @PostMapping ("/transfer")
    public void transfer(@RequestBody TransferRequest request){
        bankService.makeTransfer(request);
    }

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
