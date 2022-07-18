package com.suracki.collector.repository;

import com.suracki.collector.domain.Item;
import com.suracki.collector.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    @Query("SELECT u FROM Item u WHERE u.type = ?1")
    List<Item> findByType(String type);

    @Query("SELECT u FROM Item u WHERE u.itemName = ?1")
    List<Item> findByName(String name);

    @Query("SELECT u FROM Item u WHERE u.detail = ?1")
    List<Item> findByDetail(String detail);

    @Query("SELECT type FROM Item")
    List<String> getTypes();
}
