package app.nchu.tsc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.nchu.tsc.codegen.types.GQL_SystemVariable;
import app.nchu.tsc.models.SystemVariable;
import app.nchu.tsc.repositories.SystemVariableRepository;
import jakarta.annotation.PostConstruct;

@Service
public class SystemVariableService {

    @Autowired
    private SystemVariableRepository systemVariableRepository;

    @PostConstruct
    public void init() {
        
    }

    public String get(String key) {
        return systemVariableRepository.findById(key).orElse(
            SystemVariable.builder()
                .key(key)
                .value("")
                .build()
        ).getValue();
    }

    public static SystemVariable toSystemVariable(GQL_SystemVariable sv) {
        return SystemVariable.builder()
            .key(sv.getKey())
            .value(sv.getValue())
            .type(sv.getType())
            .defaultValue(sv.getDefaultValue())
            .detail(sv.getDetail())
            .build();
    }

    public static GQL_SystemVariable toSystemVariable(SystemVariable sv) {
        GQL_SystemVariable result = new GQL_SystemVariable();

        result.setKey(sv.getKey());
        result.setValue(sv.getValue());
        result.setType(sv.getType());
        result.setDefaultValue(sv.getDefaultValue());
        result.setDetail(sv.getDetail());

        return result;
    }
    
}
