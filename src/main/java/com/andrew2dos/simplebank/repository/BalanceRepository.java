package com.andrew2dos.simplebank.repository;

import com.andrew2dos.simplebank.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
    List<Balance> findAllByOrderByIdAsc(); // метод для сортировки по id
}
