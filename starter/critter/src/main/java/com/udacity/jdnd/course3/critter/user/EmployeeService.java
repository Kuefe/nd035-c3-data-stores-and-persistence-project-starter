package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.*;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployee(Long employeeId) {
        Employee employee;
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isPresent())
            employee = employeeOptional.get();
        else
            throw new EmployeeNotFoundException("Employee not found");
        return employee;
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, Long employeeId) {
        Employee employee = getEmployee(employeeId);
        employee.setDaysAvailable(daysAvailable);
        saveEmployee(employee);
    }

    public Set<Employee> findEmployeesForService(DayOfWeek dayOfWeek, Set<EmployeeSkill> skills) {
        Set<Employee> employeeSet = employeeRepository.findEmployeeByDaysAvailableAndSkillsIn(dayOfWeek, skills);

        // Check if the employee meets all skills
        Set<Employee> newEmployeeSet = new HashSet<>();
        for (Employee employee: employeeSet) {
            if (employee.getSkills().containsAll(skills)) newEmployeeSet.add(employee);
        }

        return newEmployeeSet;
    }
}
