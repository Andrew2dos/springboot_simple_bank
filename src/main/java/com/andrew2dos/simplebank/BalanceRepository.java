package com.andrew2dos.simplebank;

import com.andrew2dos.simplebank.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BalanceRepository extends JpaRepository<Balance, Long> {

//    private final Map<Long, BigDecimal> storage = new HashMap<>(Map.of(1L, BigDecimal.TEN));
//
//    public BigDecimal getBalanceForId(Long accountId) {
//        return storage.get(accountId);
//    }
//
//    public void save(Long id, BigDecimal amount) {
//            storage.put(id, amount);
//    }
}
