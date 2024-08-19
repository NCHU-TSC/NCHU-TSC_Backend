package app.nchu.tsc.repositories;

import app.nchu.tsc.models.Log;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findByNamespace(String namespace);
    List<Log> findByLevel(Log.Level level);
}


