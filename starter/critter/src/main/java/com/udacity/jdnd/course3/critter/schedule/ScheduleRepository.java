package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // createSchedule: save or saveAll
    // getAllSchedules: findAll

    // getScheduleForPet:
    List<Schedule> findAllByPetsId(Long petId);

    // getScheduleForEmployee
    List<Schedule> findAllByEmployeesId(Long employeeId);

    // getScheduleForCustomer
    List<Schedule> findAllByPetsCustomerId(Long customerId);
}
