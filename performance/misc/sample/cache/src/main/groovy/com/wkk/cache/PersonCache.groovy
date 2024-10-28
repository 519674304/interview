package com.wkk.cache


import com.wkk.model.Person
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component

@Component
class PersonCache {

    @Cacheable(value = "persons")
    Person getPerson(Long id) {
        println "get person from db"
        new Person(id: id, name: "wkk", address: "beijing")
    }

    @CachePut(value = "persons", key = "#person.id")
    Person putPerson(Person person) {
        person
    }

}
