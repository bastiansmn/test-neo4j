package fr.bastiansmn.testneo4j.model.relationnal

import jakarta.persistence.*

@Entity
@Table(name = "parent")
data class Parent(
    @Id
    val id: Long,

    val name: String,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    val children: MutableList<Child> = mutableListOf()
) {
    constructor() : this(-1, "")
}