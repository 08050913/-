package com.newer.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.newer.booking.entity.Department;
import com.newer.booking.mapper.DepartmentMapper;

@CrossOrigin
@RestController
@RequestMapping("/api/dept")
public class DepartmentController {
	
	@Autowired
	DepartmentMapper departmentMapper;

	//查询所有科室分类信息
	@GetMapping
	public List<Department> findAll() {
		return departmentMapper.findAll();
	}

	//根据父科室查询子科室信息
	@GetMapping("/{id}")
	public ResponseEntity<List<Department>> load(@PathVariable int id) {
		return new ResponseEntity<List<Department>>(departmentMapper.findSub(id),HttpStatus.OK);
	}
//	@GetMapping("/{id}")
//	public ResponseEntity<Department> load(@PathVariable int id) {
//		return new ResponseEntity<Department>((Department) departmentMapper.findSub(id),HttpStatus.OK);
//	}
}
