package app.nchu.tsc.services;

import app.nchu.tsc.models.Log;
import app.nchu.tsc.repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Logger {

    @Autowired
    private LogRepository logRepository;

    public List<Log> getLogs() {
        return logRepository.findAll();
    }

    public List<Log> getLogsByNamespace(String namespace) {
        return logRepository.findByNamespace(namespace);
    }

    public List<Log> getLogsByLevel(Log.Level level) {
        return logRepository.findByLevel(level);
    }

    public Log log(Log.Level level, String namespace, String detail) {
        return logRepository.save(new Log(level, namespace, detail));
    }

    public Log log(String namespace, String detail) {
        return logRepository.save(new Log(namespace, detail));
    }

}
