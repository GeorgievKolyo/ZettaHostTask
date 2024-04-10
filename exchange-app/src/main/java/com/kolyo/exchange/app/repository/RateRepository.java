package com.kolyo.exchange.app.repository;

import com.kolyo.exchange.app.model.RateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<RateEntity, Long> {

    Optional<RateEntity> findRateEntitiesByDateAndFromCurrencyAndToCurrency(LocalDate date, String fromCurrency, String toCurrency);
}
