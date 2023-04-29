package com.example.hrsys.service

import com.example.hrsys.entity.Employee
import com.example.hrsys.repository.EmployeeRepository
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class EmployeeService(val repository: EmployeeRepository) {

    fun getEmployees(): Collection<Employee> {
        return repository.findAll()
    }

    fun addEmployee(employee: Employee) {
        if (employee.age <= 0 || employee.name == "" || employee.position == "") {
            throw IllegalStateException("Wrongly filled form")
        }
        repository.save(employee)
    }

    fun getPositionsStat(): ArrayList<ArrayList<String>> {
        return repository.getPositionsStat()
    }

    fun getAgeStat(): ArrayList<ArrayList<String>> {
        return repository.getAgeStat()
    }

    fun deleteEmployee(id: Int) {
        val exists: Boolean = repository.existsById(id)
        if (!exists) {
            throw IllegalStateException("Employee with id $id does not exist")
        }
        repository.deleteById(id)
    }

    fun editEmployee(id: Int, name: String, age: Int, position: String) {
        val employee: Employee? = repository.getFirstById(id)
        if (employee == null) {
            throw IllegalStateException("Employee with id $id does not exist")
        }
        if (age <= 0) {
            throw IllegalArgumentException("Given age is not valid, enter number bigger than 0")
        }
        employee.name = name
        employee.age = age
        employee.position = position
        repository.save(employee)
    }
}
