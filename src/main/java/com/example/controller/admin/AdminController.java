package com.example.controller.admin;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin")
public class AdminController {
    @GetMapping
    public String get(){
        return "ADMIN:: GET CONTROLLER";
    }
    @PostMapping
    public String post(){
        return "ADMIN:: POST CONTROLLER";
    }
    @PutMapping
    public String put(){
        return "ADMIN:: PUT CONTROLLER";
    }
    @DeleteMapping
    public String delete(){
        return "ADMIN:: DELETE CONTROLLER";
    }
}
