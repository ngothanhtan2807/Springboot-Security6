package com.example.controller.management;

import com.example.helper.SecurityContextHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/management")
@RequiredArgsConstructor
public class ManagementController {
    private final SecurityContextHelper helper;
    @GetMapping
    public String get(){
        System.out.println(helper.getUserDetails().getUser().getFirstName());
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
