package com.example.controller.management;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/management")
public class ManagementController {
    @GetMapping
    public String get(){
        return "MANAGER:: GET CONTROLLER";
    }
    @PostMapping
    public String post(){
        return "MANAGER:: POST CONTROLLER";
    }
    @PutMapping
    public String put(){
        return "MANAGER:: PUT CONTROLLER";
    }
    @DeleteMapping
    public String delete(){
        return "MANAGER:: DELETE CONTROLLER";
    }
}
