package org.chassot.auth.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/health")
public class HealthController {

    @GetMapping
    public String getMessage() {
        return "Kickstart my heart";
    }
}
