package com.practise.grower.controller.AdminController;

import com.practise.grower.entity.Logger;
import com.practise.grower.service.LoggerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/log")
public class AdminLogsController {


    private final LoggerService loggerService;

    // get all the audit info
    @GetMapping()
    public List<Logger> getLoggers(){
        return loggerService.getLoggers();
    }


}
