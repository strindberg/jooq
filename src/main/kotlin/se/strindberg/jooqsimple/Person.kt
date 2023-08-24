package se.strindberg.jooqsimple

data class PersonIn(val firstName: String, val lastName: String, val addresses: List<Address>)

data class Person(val id: Long, val firstName: String, val lastName: String, val addresses: List<Address>)

data class Address(val line1: String, val line2: String)

data class StandaloneAddress(val line1: String, val line2: String, val person: Person)
