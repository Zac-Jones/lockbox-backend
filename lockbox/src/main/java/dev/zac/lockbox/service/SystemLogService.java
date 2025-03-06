package dev.zac.lockbox.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.zac.lockbox.entity.SystemLog;
import dev.zac.lockbox.repository.impl.SystemLogRepository;

@Service
public class SystemLogService {
    
    @Autowired
    private SystemLogRepository systemLogRepository;
    
    public SystemLog createSystemLog(SystemLog systemLog) {
        systemLogRepository.save(systemLog);
        return systemLog;
    }

    public SystemLog updateSystemLog(SystemLog systemLog) {
        systemLogRepository.update(systemLog);
        return systemLog;
    }

    public void deleteSystemLog(String id) {
        systemLogRepository.delete(id);
    }

    public SystemLog getSystemLogById(String id) throws Exception {
        return systemLogRepository.findById(id);
    }

    public List<SystemLog> getAllSystemLogs() {
        return systemLogRepository.findAll();
    }
}
