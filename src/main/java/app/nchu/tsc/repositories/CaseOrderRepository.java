package app.nchu.tsc.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.nchu.tsc.models.CaseID;
import app.nchu.tsc.models.CaseOrder;
import app.nchu.tsc.models.Member;

@Repository
public interface CaseOrderRepository extends JpaRepository<CaseOrder, UUID> {
    
    List<CaseOrder> findByMemberID(Member memberID);
    List<CaseOrder> findByCaseID(CaseID caseID);

}
