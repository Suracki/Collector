package com.suracki.collector.repository;

import com.suracki.collector.domain.Item;
import com.suracki.collector.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    @Query("SELECT u FROM Item u WHERE u.type = ?1")
    User findByType(String type);

    @Query("SELECT u FROM Item u WHERE u.detail = ?1")
    User findByDetail(String detail);
}
