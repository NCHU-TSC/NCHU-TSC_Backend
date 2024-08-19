package app.nchu.tsc.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.nchu.tsc.models.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {

    Member findByResID(UUID resID);
    
}
