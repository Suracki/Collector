package com.suracki.collector.repository;

import com.suracki.collector.domain.Location;
import com.suracki.collector.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LocationRepository extends JpaRepository<Location, Integer> {

    @Query("SELECT u FROM Location u WHERE u.storageName = ?1")
    Location findByName(String type);

}
