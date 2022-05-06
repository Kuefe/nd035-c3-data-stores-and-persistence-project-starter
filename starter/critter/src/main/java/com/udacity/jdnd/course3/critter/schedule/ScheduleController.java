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
        for (Schedule schedule : schedules) {
            schedulesDTO.add(convertScheduleToScheduleDTO(schedule));
        }
        return schedulesDTO;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<ScheduleDTO> schedulesDTO = new ArrayList<>();

        List<Schedule> schedules = scheduleService.getScheduleForPet(petId);
        for (Schedule schedule : schedules) {
            schedulesDTO.add(convertScheduleToScheduleDTO(schedule));
        }

        return schedulesDTO;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<ScheduleDTO> schedulesDTO = new ArrayList<>();

        List<Schedule> schedules = scheduleService.getScheduleForEmployee(employeeId);
        for (Schedule schedule : schedules) {
            schedulesDTO.add(convertScheduleToScheduleDTO(schedule));
        }

        return schedulesDTO;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<ScheduleDTO> schedulesDTO = new ArrayList<>();

        List<Schedule> schedules = scheduleService.getScheduleForCustomer(customerId);
        for (Schedule schedule : schedules) {
            schedulesDTO.add(convertScheduleToScheduleDTO(schedule));
        }

        return schedulesDTO;
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO, new String[]{"employees", "pets"});

        List<Long> employeeIds = new ArrayList<>();
        List<Employee> employees = schedule.getEmployees();
        for (Employee employee : employees) {
            employeeIds.add(employee.getId());
        }
        scheduleDTO.setEmployeeIds(employeeIds);

        List<Long> petIds = new ArrayList<>();
        List<Pet> pets = schedule.getPets();
        for (Pet pet : pets) {
            petIds.add(pet.getId());
        }
        scheduleDTO.setPetIds(petIds);

        return scheduleDTO;
    }

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule, new String[]{"employeeIds", "petIds"});

        List<Employee> employees = new ArrayList<>();
        List<Long> employeeIds = scheduleDTO.getEmployeeIds();
        for (Long employeeId : employeeIds) {
            employees.add(employeeService.getEmployee(employeeId));
        }
        schedule.setEmployees(employees);

        List<Pet> pets = new ArrayList<>();
        List<Long> petIds = scheduleDTO.getPetIds();
        for (Long petId : petIds) {
            pets.add(petService.getPet(petId));
        }
        schedule.setPets(pets);

        return schedule;
    }
}
