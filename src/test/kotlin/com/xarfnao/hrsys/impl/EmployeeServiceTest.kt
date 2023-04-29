package com.xarfnao.hrsys.impl

import com.xarfnao.hrsys.impl.entity.Employee
import com.xarfnao.hrsys.impl.repository.EmployeeRepository
import com.xarfnao.hrsys.impl.service.EmployeeService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

/**
 * Beware, tests in this class empty the database!
 * */
@SpringBootTest
internal class EmployeeServiceTest {
    @Autowired
    final lateinit var employeeRepository: EmployeeRepository
    @Autowired
    final lateinit var employeeService: EmployeeService

    @BeforeEach
    @AfterEach
    fun init() {
        employeeRepository.deleteAll()
        val retEmployees = employeeRepository.findAll()
        assertTrue(retEmployees.isEmpty())
    }

    @Test
    fun `should save employees and then get them`() {
        val employeeP = Employee(0, "Peter", 30, "Software Engineer")
        val employeeJ = Employee(0, "John", 35, "HR manager")
        employeeService.addEmployee(employeeP)
        employeeService.addEmployee(employeeJ)
        val retEmployees = employeeService.getEmployees()
        assertFalse(retEmployees.isEmpty())
        assertTrue(retEmployees.contains(employeeJ))
        assertTrue(retEmployees.contains(employeeP))
        assertTrue(retEmployees.size == 2)
    }

    @Test
    fun `should save employee object and then delete it by its id`() {
        val employee = Employee(0, "Peter", 30, "Software Engineer")
        employeeService.addEmployee(employee)
        val empId = employee.id
        assertTrue(employeeRepository.existsById(empId))
        employeeService.deleteEmployee(empId)
        assertFalse(employeeRepository.existsById(empId))
    }

    @Test
    fun `should add employees and get correct positions statistic`() {
        val employees = listOf(
            Employee(0, "Peter", 30, "Software Engineer"),
            Employee(0, "John", 36, "HR manager"),
            Employee(0, "Kate", 20, "Software Engineer"),
            Employee(0, "Tom", 20, "HR manager")
        )
        for (employee in employees) { employeeService.addEmployee(employee) }

        val posStatistic = employeeService.getPositionsStat()
        assertFalse(posStatistic.isEmpty())

        val controlList = arrayListOf<String>()
        controlList.addAll(listOf("Software Engineer", "2", "25.0"))
        assertTrue(posStatistic.contains(controlList))

        controlList.removeAll(listOf("Software Engineer", "2", "25.0"))
        controlList.addAll(listOf("HR manager", "2", "28.0"))
        assertTrue(posStatistic.contains(controlList))
        assertTrue(posStatistic.size == 2)
    }

    @Test
    fun `should add employees and get correct age statistic`() {
        val employees = listOf(
            Employee(0, "Peter", 10, "Software Engineer"),
            Employee(0, "John", 12, "HR manager"),
            Employee(0, "Kate", 41, "Software Engineer"),
            Employee(0, "Tom", 62, "HR manager"),
            Employee(0, "Dave", 24, "Hacker")
        )
        for (employee in employees) { employeeService.addEmployee(employee) }

        val ageStatistic = employeeService.getAgeStat()
        assertFalse(ageStatistic.isEmpty())

        val controlList = ArrayList<String>()
        controlList.addAll(listOf("2", "1", "1", "1"))
        assertTrue(ageStatistic.contains(controlList))
        assertTrue(ageStatistic.size == 1)
    }
}
