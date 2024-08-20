package app.nchu.tsc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    
}
