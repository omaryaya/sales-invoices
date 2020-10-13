package com.omaryaya.jetbrains.controller;

import java.util.List;

import com.omaryaya.jetbrains.model.OrderStatusCount;
import com.omaryaya.jetbrains.payload.ApiResponse;
import com.omaryaya.jetbrains.security.CurrentUser;
import com.omaryaya.jetbrains.security.UserPrincipal;
import com.omaryaya.jetbrains.service.DashboardService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@Cacheable("dashboard")
public class DashboardController {

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private DashboardService dashboardService;

    
    // Create

    @GetMapping("/totalprofits")
    public ResponseEntity<?> totalProfits(@CurrentUser final UserPrincipal currentUser) {
        try {

            final List<?> profitsPerOrder = dashboardService.getProfitsForOrders();

            return ResponseEntity.ok().body(new ApiResponse<List<?>>(true, "Order profits", profitsPerOrder));
        } catch (final Exception ex) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Failed to retrieve profits. " + ex.getMessage()));
        }
    }

    @GetMapping("/yearprofit")
    public ResponseEntity<?> yearProfit(@CurrentUser final UserPrincipal currentUser) {
        try {

            final Long profit = dashboardService.getYearProfit();

            return ResponseEntity.ok().body(new ApiResponse<Long>(true, "Current Year profit", profit));
        } catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Failed to retrieve Current Year profit. " + ex.getMessage()));
        }
    }

    @GetMapping("/orders/status")
    public ResponseEntity<?> groupOrdersByStatus(@CurrentUser final UserPrincipal currentUser) {
        try {

            final List<OrderStatusCount> orderStatusCounts= dashboardService.groupOrdersByStatus();

            return ResponseEntity.ok().body(new ApiResponse<List<?>>(true, "Orders Status Count", orderStatusCounts));
        } catch (final Exception ex) {
            logger.error(ex.getMessage(), ex);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Unable to find Orders Status Count."));
        }
    }



}
