package com.udacity.jdnd.course3.critter.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // saveEmployee: saveAll
    // getEmployee: findAllById

    // setAvailability
    List<Employee> findEmployeeByIdAndDaysAvailable(Long employeeId, DayOfWeek daysAvailable);

    // findEmployeesForService
    List<Employee> findEmployeeByIdAndSkills(Long employeeId, EmployeeSkill skill);
}
