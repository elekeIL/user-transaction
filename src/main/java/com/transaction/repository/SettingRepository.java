package com.transaction.repository;

import com.transaction.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Long> {

    Setting findByName(String name);

    List<Setting> findByNameStartingWith(String name);

    Optional<Setting> findSettingByName(String name);

}