package com.erp.controller.admin;

import com.erp.dto.AdminDashboardDTO;
import com.erp.service.AdminDashboardAggregatorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/dashboard")
public class AdminDashboardApiController {

    private final AdminDashboardAggregatorService service;

    public AdminDashboardApiController(AdminDashboardAggregatorService service) {
        this.service = service;
    }

    @GetMapping("/data")
    public AdminDashboardDTO getDashboardData() {
        return service.getDashboard();
    }
}