package app.nchu.tsc.services;

import app.nchu.tsc.codegen.types.GQL_Role;
import app.nchu.tsc.models.Role;
import app.nchu.tsc.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleByName(String name) {
        return roleRepository.findById(name).orElse(null);
    }

    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    public Role updateRole(Role role) {
        return roleRepository.save(role);
    }

    public static GQL_Role toRole(Role role) {
        GQL_Role result = new GQL_Role();

        result.setName(role.getName());
        result.setCanViewLog(role.getCanViewLog());
        result.setCanModifyRole(role.getCanModifyRole());
        result.setCanModifyMember(role.getCanModifyMember());
        result.setCanModifyCase(role.getCanModifyCase());
        result.setCanModifyCaseOrder(role.getCanModifyCaseOrder());
        result.setCanModifyBankRecord(role.getCanModifyBankRecord());

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
