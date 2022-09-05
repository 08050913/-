package com.newer.booking.controller;

import com.newer.booking.entity.Appointment;
import com.newer.booking.mapper.AppointmentMapper;
import com.newer.booking.mapper.PatientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/apt")
public class AppointmentController {
/*
@ResponseBody可以直接返回Json结果，
@ResponseEntity不仅可以返回json结果，还可以定义返回的HttpHeaders和HttpStatus
 */
	@Autowired
	AppointmentMapper appointmentMapper;

	@Autowired
	SchedulingController schedulingController;

	@Autowired
	PatientMapper patientMapper;

	@Autowired
	NumController numController;

	@GetMapping
	public ResponseEntity<List<Appointment>> findAll() {
		return new ResponseEntity<List<Appointment>>(appointmentMapper.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Appointment> load(@PathVariable int id) {
		return new ResponseEntity<Appointment>(appointmentMapper.findById(id), HttpStatus.OK);
	}

	@PostMapping
	@Transactional // 开启事务
	public ResponseEntity<Appointment> create(@RequestBody Appointment appointment) {
		appointment.setNum(numController.getNumInBack());
		appointment.setGenerate(new Date(System.currentTimeMillis()));
		appointmentMapper.save(appointment);
		boolean flag = schedulingController.addCount(appointment.getDoctor().getId(), appointment.getClinic().getId(), appointment.getTime());
		if (flag) { // 预约成功
			patientMapper.remove(appointment.getPatient().getId());
			return new ResponseEntity<Appointment>(appointment, HttpStatus.OK);
		}
		else {
			throw new RuntimeException("诊所人数已经满");
		}
	}

//	@PutMapping("/{id}")
//	public ResponseEntity<Appointment> update(
//			@PathVariable int id,
//			@RequestBody Appointment appointment) {
//		appointment.setId(id);
//		appointmentMapper.update(appointment);
//
//		return new ResponseEntity<>(appointment, HttpStatus.OK);
//	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<String> remove(
			@PathVariable int id) {

		appointmentMapper.remove(id);
		// 将 scheduling 表的 count -1
		schedulingController.delCount(id);

		return new ResponseEntity<String>(HttpStatus.OK);
	}

}
