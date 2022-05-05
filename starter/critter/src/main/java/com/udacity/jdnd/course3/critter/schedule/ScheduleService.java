package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForPet(Long petId) {
        return scheduleRepository.findAllByPetsId(petId);
    }

    public List<Schedule> getScheduleForEmployee(Long employeeId) {
        return scheduleRepository.findAllByEmployeesId(employeeId);
    }

    public List<Schedule> getScheduleForCustomer(Long customerId) {
        return scheduleRepository.findAllByPetsCustomerId(customerId);
    }
}
