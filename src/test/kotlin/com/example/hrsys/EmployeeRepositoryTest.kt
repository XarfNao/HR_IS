package com.example.hrsys

import com.example.hrsys.entity.Employee
import com.example.hrsys.repository.EmployeeRepository
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

/**
 * Beware, tests in this class empty the database!
 * */
@SpringBootTest
internal class EmployeeRepositoryTest {
    @Autowired
    final lateinit var employeeRepository: EmployeeRepository

    @BeforeEach
    @AfterEach
    fun init() {
        employeeRepository.deleteAll()
        val retEmployees = employeeRepository.findAll()
        assertTrue(retEmployees.isEmpty())
    }

    @Test
    fun `should save employee object and then delete it by its id`() {
        val employee = Employee(0, "Peter", 30, "Software Engineer")
        employeeRepository.save(employee)
        val empId = employee.id
        val retEmployee = employeeRepository.findByIdOrNull(empId)
        assertEquals(retEmployee, employee)
        employeeRepository.deleteById(empId)
        assertNull(employeeRepository.findByIdOrNull(empId))
    }

    @Test
    fun `should save employees and give collection of all employees`() {
        val employeeP = Employee(0, "Peter", 30, "Software Engineer")
        val employeeJ = Employee(0, "John", 35, "HR manager")
        employeeRepository.save(employeeP)
        employeeRepository.save(employeeJ)
        val retEmployees = employeeRepository.findAll()
        assertFalse(retEmployees.isEmpty())
        assertTrue(retEmployees.contains(employeeP))
        assertTrue(retEmployees.contains(employeeJ))
        assertTrue(retEmployees.size == 2)
    }

    @Test
    fun `should save employees and give correct positions statistic`() {
        val employees = listOf(Employee(0, "Peter", 30, "Software Engineer"),
                               Employee(0, "John", 36, "HR manager"),
                               Employee(0, "Kate", 20, "Software Engineer"),
                               Employee(0, "Tom", 20, "HR manager"))
        for (employee in employees) { employeeRepository.save(employee) }

        val posStatistic = employeeRepository.getPositionsStat()
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
    fun `should save employees and give correct age statistic`() {
        val employees = listOf(Employee(0, "Peter", 10, "Software Engineer"),
            Employee(0, "John", 12, "HR manager"),
            Employee(0, "Kate", 41, "Software Engineer"),
            Employee(0, "Tom", 62, "HR manager"),
            Employee(0, "Dave", 24, "Hacker"))
        for (employee in employees) { employeeRepository.save(employee) }

        val ageStatistic = employeeRepository.getAgeStat()
        assertFalse(ageStatistic.isEmpty())

        val controlList = ArrayList<String>()
        controlList.addAll(listOf("2", "1", "1", "1"))
        assertTrue(ageStatistic.contains(controlList))
        assertTrue(ageStatistic.size == 1)
    }
}
