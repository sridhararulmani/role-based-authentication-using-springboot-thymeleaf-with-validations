package com.project.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.backend.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

	public Optional<Account> findByUserEmail(String userEmail);

	public boolean existsByUserEmail(String userEmail);

}
