package com.andrew2dos.simplebank;

import com.andrew2dos.simplebank.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Balance, Long> {

}
