package com.example.controller;

import com.example.service.ExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/demo")
@RequiredArgsConstructor
public class DemoController {
private final ExcelService excelService;
@GetMapping
    public String demo() throws IOException, NoSuchMethodException {
   excelService.writeExcel();
    return "demo api";
}
}
