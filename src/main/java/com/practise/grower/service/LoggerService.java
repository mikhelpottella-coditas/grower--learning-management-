package com.practise.grower.service;


import com.practise.grower.entity.Logger;
import com.practise.grower.repository.LoggerRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoggerService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LoggerService.class);

    private final LoggerRepo loggerRepo;


    public List<Logger> getLoggers() {
        logger.info("getting all the audit info about this app");
        return loggerRepo.findAll();
    }
}
