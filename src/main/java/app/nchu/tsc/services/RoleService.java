package app.nchu.tsc.services;

import java.util.HashMap;

import app.nchu.tsc.codegen.types.GQL_Role;
import app.nchu.tsc.models.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public int updateColumnById(String name, String column, boolean value) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Role> update = cb.createCriteriaUpdate(Role.class);
        Root<Role> root = update.from(Role.class);

        update.set(root.get(column), value);
        update.where(cb.equal(root.get("name"), name));

        return entityManager.createQuery(update).executeUpdate();
    }

    @Transactional
    public int updateColumnsById(String name, HashMap<String, Boolean> columns) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Role> update = cb.createCriteriaUpdate(Role.class);
        Root<Role> root = update.from(Role.class);

        for (String column : columns.keySet()) {
            update.set(root.get(column), columns.get(column));
        }

        update.where(cb.equal(root.get("name"), name));

        return entityManager.createQuery(update).executeUpdate();
    }

    public static GQL_Role toRole(Role role) {
        GQL_Role result = new GQL_Role();

        result.setName(role.getName());
        result.setCanViewLog(role.isCanViewLog());
        result.setCanModifyRole(role.isCanModifyRole());
        result.setCanModifyMember(role.isCanModifyMember());
        result.setCanModifyCase(role.isCanModifyCase());
        result.setCanModifyCaseOrder(role.isCanModifyCaseOrder());
        result.setCanModifyBankRecord(role.isCanModifyBankRecord());

        return result;
    }

    public static Role toRole(GQL_Role role) {
        return Role.builder()
                .name(role.getName())
                .canViewLog(role.getCanViewLog())
                .canModifyRole(role.getCanModifyRole())
                .canModifyMember(role.getCanModifyMember())
                .canModifyCase(role.getCanModifyCase())
                .canModifyCaseOrder(role.getCanModifyCaseOrder())
                .canModifyBankRecord(role.getCanModifyBankRecord())
                .build();
    }

}
