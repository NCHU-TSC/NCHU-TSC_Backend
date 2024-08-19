package app.nchu.tsc.repositories;

import app.nchu.tsc.models.Redirecting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedirectingRepository extends JpaRepository<Redirecting, String> {

}