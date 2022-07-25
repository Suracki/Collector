package com.suracki.collector.repository;

import com.suracki.collector.domain.MtgCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface MtgCardRepository extends JpaRepository<MtgCard, Integer> {

    @Query("SELECT u FROM MtgCard u WHERE u.set_code = ?1 AND u.collector_number = ?2")
    MtgCard findBySetAndCollectorNumber(String set_code, String collector_number);

}
