package app.nchu.tsc.controllers;

import java.util.List;
import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;

import app.nchu.tsc.codegen.types.GQL_Role;
import app.nchu.tsc.codegen.types.GQL_RolePairInput;
import app.nchu.tsc.exceptions.PermissionDeniedException;
import app.nchu.tsc.models.Member;
import app.nchu.tsc.repositories.RoleRepository;
import app.nchu.tsc.services.MemberService;
import app.nchu.tsc.services.RoleService;

@DgsComponent
public class RoleController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    @DgsMutation
    private GQL_Role setRoleData(@CookieValue UUID member_id, @CookieValue String member_token, @InputArgument String roleName, @InputArgument List<GQL_RolePairInput> data) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null || !operator.getRole().isCanModifyRole()) {
            throw new PermissionDeniedException("Permission Denied");
        }

        HashMap<String, Boolean> permissions = new HashMap<String, Boolean>();
        for(GQL_RolePairInput pair : data) {
            permissions.put(pair.getKey(), pair.getValue());
        }

        roleService.updateColumnsById(roleName, permissions);

        return RoleService.toRole(roleRepository.findById(roleName).orElse(null));

    }
    
}
