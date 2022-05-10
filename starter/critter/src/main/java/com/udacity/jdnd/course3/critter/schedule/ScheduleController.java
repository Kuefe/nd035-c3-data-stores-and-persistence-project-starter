package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @Autowired
    PetService petService;

    @Autowired
    EmployeeService employeeService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleService.createSchedule(convertScheduleDTOToSchedule(scheduleDTO));
        return convertScheduleToScheduleDTO(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> schedulesDTO = new ArrayList<>();
        List<Schedule> schedules = scheduleService.getAllSchedules();
        return schedules.stream()
                .map(p -> convertScheduleToScheduleDTO(p))
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<ScheduleDTO> schedulesDTO = new ArrayList<>();

        List<Schedule> schedules = scheduleService.getScheduleForPet(petId);
        return schedules.stream()
                .map(p -> convertScheduleToScheduleDTO(p))
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<ScheduleDTO> schedulesDTO = new ArrayList<>();

        List<Schedule> schedules = scheduleService.getScheduleForEmployee(employeeId);
        return schedules.stream()
                .map(p -> convertScheduleToScheduleDTO(p))
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<ScheduleDTO> schedulesDTO = new ArrayList<>();

        List<Schedule> schedules = scheduleService.getScheduleForCustomer(customerId);
        return schedules.stream()
                .map(p -> convertScheduleToScheduleDTO(p))
                .collect(Collectors.toList());
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO, new String[]{"employees", "pets"});

        List<Employee> employees = schedule.getEmployees();
        List<Long> employeeIds = employees.stream().map(e -> e.getId()).collect(Collectors.toList());
        scheduleDTO.setEmployeeIds(employeeIds);

        List<Pet> pets = schedule.getPets();
        List<Long> petIds = pets.stream().map(p -> p.getId()).collect(Collectors.toList());
        scheduleDTO.setPetIds(petIds);

        return scheduleDTO;
    }

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule, new String[]{"employeeIds", "petIds"});

        List<Long> employeeIds = scheduleDTO.getEmployeeIds();
        List<Employee> employees = employeeIds.stream()
                .map(e->employeeService.getEmployee(e)).collect(Collectors.toList());
        schedule.setEmployees(employees);

        List<Long> petIds = scheduleDTO.getPetIds();
        List<Pet> pets = petIds.stream()
                .map(p->petService.getPet(p))
                .collect(Collectors.toList());
        schedule.setPets(pets);

        return schedule;
    }
}
