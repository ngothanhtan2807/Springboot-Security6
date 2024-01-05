package com.example.controller.management;

import com.example.helper.SecurityContextHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/management")
@RequiredArgsConstructor
@Tag(name = "Management", description = "Api for Management")
public class ManagementController {
    private final SecurityContextHelper helper;
    @GetMapping
    @Operation(summary = "Get")
    public String get(){
        System.out.println(helper.getUserDetails().getUser().getFirstName());
        return "MANAGER:: GET CONTROLLER";
    }
    @PostMapping
    @Operation(summary = "Post")
    public String post(){
        return "MANAGER:: POST CONTROLLER";
    }
    @PutMapping
    @Operation(summary = "Put")
    public String put(){
        return "MANAGER:: PUT CONTROLLER";
    }
    @DeleteMapping
    @Operation(summary = "Delete")
    public String delete(){
        return "MANAGER:: DELETE CONTROLLER";
    }
}
