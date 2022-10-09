package com.example.hrsys.controller

import com.example.hrsys.service.EmployeeService
import com.example.hrsys.entity.Employee
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("api/employee")
class EmployeeController(val service: EmployeeService) {

    @GetMapping("/all")
    fun getEmployees(model: Model): String {
        model.addAttribute("employees", service.getEmployees())
        return "all_employees"
    }

    @GetMapping("/pos_overview")
    fun getPosOverview(model: Model): String {
        model.addAttribute("statistic", service.getPositionsStat())
        return "positions_overview"
    }

    @GetMapping("/age_categ")
    fun getAgeCategories(model: Model): String {
        model.addAttribute("statistic", service.getAgeStat())
        return "age_categories"
    }

    @GetMapping("/add")
    fun addEmployee(model: Model): String {
        val employee = Employee(0, "", 0, "")
        model.addAttribute("employee", employee)
        return "add_form"
    }
    @PostMapping("/add")
    fun addEmployee(@ModelAttribute employee: Employee): String {
        service.addEmployee(employee)
        return "add_success"
    }

    @GetMapping("/del")
    fun deleteEmployee(model: Model): String {
        model.addAttribute("id", 0)
        return "del_form"
    }
    @PostMapping("/del")
    fun deleteEmployee(model: Model, @RequestParam(value = "id") id: Int): String {
        service.deleteEmployee(id)
        return "del_success"
    }
}
