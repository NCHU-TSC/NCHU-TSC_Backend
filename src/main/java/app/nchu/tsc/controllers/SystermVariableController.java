package app.nchu.tsc.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import app.nchu.tsc.codegen.types.GQL_SystemVariable;
import app.nchu.tsc.codegen.types.GQL_SystemVariableInput;
import app.nchu.tsc.exceptions.PermissionDeniedException;
import app.nchu.tsc.exceptions.RequestedResourceNotFound;
import app.nchu.tsc.exceptions.UnauthenticatedException;
import app.nchu.tsc.models.Member;
import app.nchu.tsc.models.SystemVariable;
import app.nchu.tsc.repositories.SystemVariableRepository;
import app.nchu.tsc.services.MemberService;
import app.nchu.tsc.services.SystemVariableService;

@DgsComponent
public class SystermVariableController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private SystemVariableRepository systemVariableRepository;

    @Autowired
    private SystemVariableService systemVariableService;

    @DgsQuery
    private String systemVariable(@CookieValue UUID member_id, @CookieValue String member_token, @InputArgument String key) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null) throw new UnauthenticatedException();

        if(!operator.getRole().isCanViewSystemVariable()) throw new PermissionDeniedException(member_id);

        return systemVariableService.get(key);
    }

    @DgsQuery
    private List<GQL_SystemVariable> systemVariables(@CookieValue UUID member_id, @CookieValue String member_token) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null) throw new UnauthenticatedException();

        if(!operator.getRole().isCanViewSystemVariable()) throw new PermissionDeniedException(member_id);

        List<GQL_SystemVariable> result = new ArrayList<>();
        List<SystemVariable> systemVariables = systemVariableRepository.findAll();

        for(SystemVariable sv : systemVariables) {
            result.add(SystemVariableService.toSystemVariable(sv));
        }

        return result;
    }

    @DgsMutation
    private GQL_SystemVariable addSystemVariable(@CookieValue UUID member_id, @CookieValue String member_token, @InputArgument GQL_SystemVariableInput data) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null) throw new UnauthenticatedException();

        if(!operator.getRole().isCanModifySystemVariable()) throw new PermissionDeniedException(member_id);

        return SystemVariableService.toSystemVariable(systemVariableRepository.save(
            SystemVariable.builder()
                .key(data.getKey())
                .value(data.getValue())
                .type(data.getType())
                .defaultValue(data.getDefaultValue())
                .detail(data.getDetail())
                .build()
        ));
    }

    @DgsMutation
    private GQL_SystemVariable setSystemVariable(@CookieValue UUID member_id, @CookieValue String member_token, @InputArgument String key, @InputArgument String value) {
        Member operator = memberService.verifyWithToken(member_id, member_token);
        if(operator == null) throw new UnauthenticatedException();

        if(!operator.getRole().isCanModifySystemVariable()) throw new PermissionDeniedException(member_id);

        SystemVariable sv = systemVariableRepository.findById(key).orElse(null);
        if(sv == null) throw new RequestedResourceNotFound("System Variable", key);

        sv.setValue(value);

        return SystemVariableService.toSystemVariable(systemVariableRepository.save(sv));
    }
    
}
