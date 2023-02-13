package jooqsimple.model;

data class Person(val id: Long, val firstName: String, val lastName: String, val addresses: List<Address>)

data class Address(val line1 :String, val line2 :String)
