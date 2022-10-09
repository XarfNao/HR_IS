package com.example.hrsys.entity

import javax.persistence.*

@Entity
@Table
data class Employee(
    @Id
    @SequenceGenerator(name = "employee_sequence", sequenceName = "employee_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_sequence")
    var id: Int = 0,
    var name: String,
    var age: Int,
    var position: String)
