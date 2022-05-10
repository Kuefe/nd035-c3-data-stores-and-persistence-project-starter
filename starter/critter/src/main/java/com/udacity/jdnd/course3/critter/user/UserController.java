package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    CustomerService customerService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = customerService.saveCustomer(convertCustomerDTOToCustomer(customerDTO));
        return convertCustomerToCustomerDTO(customer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return customers.stream()
                .map(c->convertCustomerToCustomerDTO(c))
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        Pet pet = petService.getPet(petId);
        return convertCustomerToCustomerDTO(customerService.getOwnerByPet(pet));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeService.saveEmployee(convertEmployeeDTOToEmployee(employeeDTO));
        return convertEmployeeToEmployeeDTO(employee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return convertEmployeeToEmployeeDTO(employeeService.getEmployee(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        DayOfWeek dayOfWeek = employeeDTO.getDate().getDayOfWeek();
        Set<EmployeeSkill> skills = employeeDTO.getSkills();

        Set<Employee> employees = employeeService.findEmployeesForService(dayOfWeek, skills);

        return employees.stream()
                .map(e->convertEmployeeToEmployeeDTO(e))
                .collect(Collectors.toList());
    }

    private CustomerDTO convertCustomerToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO, "pets");

        List<Long> petIds = new ArrayList<>();
        List<Pet> pets = customer.getPets();
        if (pets != null) {
            petIds = pets.stream()
                    .map(p->p.getId()).collect(Collectors.toList());
        }
        customerDTO.setPetIds(petIds);

        return customerDTO;
    }

    private Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer, "petIds");

        List<Pet> pets = new ArrayList<>();
        List<Long> petIds = customerDTO.getPetIds();
        if (petIds != null) {
            pets=petIds.stream()
                    .map(p->petService.getPet(p))
                    .collect(Collectors.toList());
        }
        customer.setPets(pets);

        return customer;
    }

    private EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    private Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }
}
