package com.newer.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.newer.booking.entity.Doctor;
import com.newer.booking.mapper.DoctorMapper;

@CrossOrigin
@RestController
@RequestMapping("/api/doctor")
public class DoctorController {
	
	@Autowired
	DoctorMapper doctorMapper;

	//查询所有医生信息
	@GetMapping
	public List<Doctor> findAll() {
		return doctorMapper.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Doctor> findById(@PathVariable int id) {
		return new ResponseEntity<Doctor>(doctorMapper.findById(id), HttpStatus.OK);
	}

	@GetMapping("/dept/{id}")
	public List<Doctor> findByDepartmentId(@PathVariable int id) {
		return doctorMapper.findByDepartmentId(id);
	}
}
