package com.example.hrsys.repository

import com.example.hrsys.entity.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : JpaRepository<Employee, Int> {

    @Query("SELECT e.position, COUNT(e.position) as pos_count, FLOOR(ROUND(AVG(e.age), 0)) " +
            "FROM Employee as e " +
            "GROUP BY e.position " +
            "ORDER BY pos_count DESC")
    fun getPositionsStat(): ArrayList<ArrayList<String>>

    @Query(value = "SELECT SUM(CASE WHEN age < 20 THEN 1 END) as under20," +
            "SUM(CASE WHEN age >= 20 AND age < 40 THEN 1 END) as above19," +
            "SUM(CASE WHEN age >= 40 AND age < 60 THEN 1 END) as above39," +
            "SUM(CASE WHEN age >= 60 THEN 1 END) as above59 FROM Employee", nativeQuery = true)
    fun getAgeStat(): ArrayList<ArrayList<String>>

    fun getFirstById(id: Int): Employee?
}
