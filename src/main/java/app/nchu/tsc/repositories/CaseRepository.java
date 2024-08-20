package app.nchu.tsc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.nchu.tsc.models.Case;
import app.nchu.tsc.models.CaseID;

@Repository
public interface CaseRepository extends JpaRepository<Case, CaseID> {

}