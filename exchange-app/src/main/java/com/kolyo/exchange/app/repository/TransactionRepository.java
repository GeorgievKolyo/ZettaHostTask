package com.kolyo.exchange.app.repository;

import com.kolyo.exchange.app.model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    Optional<TransactionEntity> findById(Long transactionId);

    List<TransactionEntity> findAllByDate(LocalDate date);
}
