package app.nchu.tsc.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.nchu.tsc.models.BankRecord;

@Repository
public interface BankRecordRepository extends JpaRepository<BankRecord, UUID> {
    
}
