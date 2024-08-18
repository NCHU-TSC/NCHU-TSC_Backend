package app.nchu.tsc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.nchu.tsc.models.SystemVariable;

@Repository
public interface SystemVariableRepository extends JpaRepository<SystemVariable, String> {
    
}
