package com.newer.booking.mapper;

import com.newer.booking.entity.Appointment;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface AppointmentMapper{
    @Select("select id,patient,doctor,time,num,clinic,fees,generate,state from appointment where id=#{id}")
    @Results(value = {
            @Result(
                    column = "patient",
                    property = "patient",
                    one = @One(select = "com.newer.booking.mapper.PatientMapper.findById")),
            @Result(
                    column = "doctor",
                    property = "doctor",
                    one = @One(select = "com.newer.booking.mapper.DoctorMapper.findById")),
            @Result(
                    column = "clinic",
                    property = "clinic",
                    one = @One(select = "com.newer.booking.mapper.ClinicMapper.findByTitle"))
    })
    Appointment findById(int id);

    @Select("select id,patient,doctor,time,num,clinic,fees,generate,state from appointment where state=1")
    @Results(value = {
            @Result(
                    column = "patient",
                    property = "patient",
                    one = @One(select = "com.newer.booking.mapper.PatientMapper.findById")),
            @Result(
                    column = "doctor",
                    property = "doctor",
                    one = @One(select = "com.newer.booking.mapper.DoctorMapper.findById")),
            @Result(
                    column = "clinic",
                    property = "clinic",
                    jdbcType= JdbcType.VARCHAR,
                    one = @One(select = "com.newer.booking.mapper.ClinicMapper.findByTitle"))
    })
    List<Appointment> findAll();

    @Insert("insert into appointment(patient,doctor,time,num,clinic,fees,generate)" +
            "values(#{apt.patient.id}, #{apt.doctor.id}, #{apt.time}, #{apt.num}, #{apt.clinic.title}, #{apt.clinic.fees},#{apt.generate})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void save(@Param("apt") Appointment appointment);

    @Update("update appointment " +
            "set patient=#{apt.patient.id}, doctor=#{apt.doctor.id}, time=#{time}, num=#{num}, clinic=#{apt.clinic.id}, fees=#{apt.clinic.fees}, generate=#{generate} " +
            "where id=#{apt.id}")
    void update(@Param("apt") Appointment appointment);

    @Update("update appointment set state=2 where id=#{id}")
    void remove(int id);
}
