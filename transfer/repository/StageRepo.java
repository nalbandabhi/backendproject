package com.bank.transfer.repository;

import com.bank.transfer.model.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StageRepo extends JpaRepository<Stage,Integer> {

}
