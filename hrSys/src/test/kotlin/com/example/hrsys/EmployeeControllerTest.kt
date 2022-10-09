package com.example.hrsys

import com.example.hrsys.entity.Employee
import com.example.hrsys.repository.EmployeeRepository
import com.example.hrsys.service.EmployeeService
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

/**
 * Beware, some tests in this class empty the database!
 */
@AutoConfigureMockMvc
@SpringBootTest
internal class EmployeeControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var employeeService: EmployeeService
    @Autowired
    private lateinit var employeeRepository: EmployeeRepository

    fun getTestCore(url: String, contains_string: String) {
        val mvcResult = mockMvc.get(url).andExpect {
            status { isOk() }
            content {
                contentTypeCompatibleWith("text/html;charset=UTF-8")
            }
        }
        val response = mvcResult.andReturn().response.contentAsString
        assertTrue(response.contains(contains_string))
    }

    @Test
    fun `should return all_employees file`() {
        getTestCore("/api/employee/all", "<title>Employee List</title>")
    }

    @Test
    fun `should return positions_overview file`() {
        getTestCore("/api/employee/pos_overview", "<title>Positions Overview Statistic</title>")
    }

    @Test
    fun `should return age_categories file`() {
        getTestCore("/api/employee/age_categ", "<title>Age Categories Statistic</title>")
    }

    @Test
    fun `should return add_form file`() {
        getTestCore("/api/employee/add", "<title>Add Employee</title>")
    }

    @Test
    fun `should return del_form file`() {
        getTestCore("/api/employee/del", "<title>Remove Employee</title>")
    }

    @Test
    fun `should add employee and return add_success file`() {
        employeeRepository.deleteAll()
        val mvcResult = mockMvc.post("/api/employee/add") {
            param("name", "John")
            param("age", "42")
            param("position", "Software Engineer")
        }.andExpect {
            status { isOk() }
            content {
                contentTypeCompatibleWith("text/html;charset=UTF-8")
            }
        }

        val response = mvcResult.andReturn().response.contentAsString
        assertTrue(response.contains("<title>Employee Added Successfully</title>"))
        val retEmployees = employeeService.getEmployees()
        assertTrue(retEmployees.size == 1)
        for (retEmployee in retEmployees) {
            assertTrue(retEmployee.name == "John")
            assertTrue(retEmployee.age == 42)
            assertTrue(retEmployee.position == "Software Engineer")
        }
        employeeRepository.deleteAll()
    }

    @Test
    fun `should delete employee and return del_success file`() {
        employeeRepository.deleteAll()
        val employee = Employee(0, "John", 42, "Software Engineer")
        employeeRepository.save(employee)
        val employeeId = employee.id

        val mvcResult = mockMvc.post("/api/employee/del") {
            param("id", employeeId.toString())
        }.andExpect {
            status { isOk() }
            content {
                contentTypeCompatibleWith("text/html;charset=UTF-8")
            }
        }

        val response = mvcResult.andReturn().response.contentAsString
        assertTrue(response.contains("<title>Employee Removed Successfully</title>"))
        val retEmployees = employeeService.getEmployees()
        assertTrue(retEmployees.isEmpty())
    }
}
