package com.newer.booking.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.newer.booking.entity.Appointment;
import com.newer.booking.mapper.AppointmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.newer.booking.mapper.SchedulingMapper;

@CrossOrigin
@RestController
@RequestMapping("/api/schedulingMapper")
public class SchedulingController {
	
	@Autowired
	SchedulingMapper schedulingMapper;

	@Autowired
	AppointmentMapper appointmentMapper;

	//门诊排班按照科室id
	@GetMapping("/dept/{id}")
	public List<HashMap<String, Object>> findByDeptId(@PathVariable int id) {
		return schedulingMapper.findByDeptId(id);
	}

	//门诊排班按照医生id
	@GetMapping("/doctor/{id}")
	public List<HashMap<String, Object>> findByDoctorId(@PathVariable int id) {
		return schedulingMapper.findByDoctorId(id);
	}

	//门诊人数+1
	public boolean addCount(int doctorId, int clinicId, Date time) {
		// 获取门诊数, 如果超过 5 人, 则失败
		int count = schedulingMapper.getCount(doctorId, clinicId, time);
		if (count >= 5) {
			return false;
		} else {
			schedulingMapper.updateCount(doctorId, clinicId, time);
			return true;
		}
	}

	public void delCount(int appointmentId) {
		// 根据 appointmentId 查的 appointment 对象
		Appointment appointment = appointmentMapper.findById(appointmentId);
		schedulingMapper.delCount(appointment.getDoctor().getId(), appointment.getClinic().getId(), appointment.getTime());
	}
}
