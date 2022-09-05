package com.newer.booking.mapper;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface SchedulingMapper {

	/**
	 * SELECT * FROM scheduling
	 * WHERE doctor IN (SELECT id FROM doctor WHERE department = 5)
	 *
	 * SELECT * FROM scheduling
	 * WHERE EXISTS (SELECT * FROM doctor WHERE scheduling.doctor = doctor.id AND doctor.department = 5)
	 */
	@Select("SELECT * FROM scheduling WHERE EXISTS (SELECT * FROM doctor WHERE scheduling.doctor = doctor.id AND doctor.department = #{id})")
	@Results(value = {
			@Result(
					column = "clinic",
					property = "clinic",
					one = @One(select = "com.newer.booking.mapper.ClinicMapper.findById")),
			@Result(
					column = "doctor",
					property = "doctor",
					one = @One(select = "com.newer.booking.mapper.DoctorMapper.findById"))
	})
	List<HashMap<String, Object>> findByDeptId(int id);

	@Select("select * from scheduling where doctor=#{id}")
	@Results(value = {
			@Result(
					column = "clinic",
					property = "clinic",
					one = @One(select = "com.newer.booking.mapper.ClinicMapper.findById"))
	})
	List<HashMap<String, Object>> findByDoctorId(int id);

	@Select("select count from scheduling where doctor=#{doctorId} and clinic=#{clinicId} and time=#{time}")
	int getCount(int doctorId, int clinicId, Date time);

	@Update("update scheduling set count=count+1 where doctor=#{doctorId} and clinic=#{clinicId} and time=#{time}")
	void updateCount(int doctorId, int clinicId, Date time);

	@Update("update scheduling set count=count-1 where doctor=#{doctorId} and clinic=#{clinicId} and time=#{time}")
	void delCount(int doctorId, int clinicId, Date time);
}
