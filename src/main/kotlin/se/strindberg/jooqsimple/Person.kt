package se.strindberg.jooqsimple

data class Person(val id: Long, val firstName: String, val lastName: String, val addresses: List<Address>, val pets: List<Pet>) {
    companion object {
        fun toPerson(id: Long, firstName: String, lastName: String) = Person(id, firstName, lastName, emptyList(), emptyList())
    }
}

data class Address(val line1: String, val line2: String)

data class AddressChild(val line1: String, val line2: String, val person: Person)

data class Pet(val name: String)
