package com.vihaan.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vihaan.entity.Profile;

public interface ProfileRepo extends JpaRepository<Profile, Long> {

}
