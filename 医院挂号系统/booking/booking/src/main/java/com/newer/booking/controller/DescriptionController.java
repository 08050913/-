package com.newer.booking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/api/description")
public class DescriptionController {
    private static final String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
    @GetMapping
    public ResponseEntity<String> load() {
        File file = new File(path + "/static/description.txt");
//        System.out.println(file.getAbsolutePath());
        if (!file.exists()) throw new RuntimeException("文件不存在！");
        byte[] buffer = new byte[1024];
        StringBuilder builder = new StringBuilder();
        try (FileInputStream in = new FileInputStream(file)) {
            int index = 0;
            while ((index = in.read(buffer)) != -1) {
                builder.append(new String(buffer, 0, index));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<String>(builder.toString(), HttpStatus.OK);
    }
}
