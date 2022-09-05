package com.newer.booking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@CrossOrigin
@RestController
@RequestMapping("/api/num")
public class NumController { // 订单流水号

    private final static Random random = new Random();

    private final static int init = 100000; // 6 位订单号

    @GetMapping
    public ResponseEntity<Integer> getNum() {
        return new ResponseEntity<Integer>(init + random.nextInt(900000), HttpStatus.OK);
    }

    public int getNumInBack() {
        return init + random.nextInt(900000);
    }
}
