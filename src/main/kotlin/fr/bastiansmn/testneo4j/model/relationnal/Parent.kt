package fr.bastiansmn.testneo4j.model.relationnal

import jakarta.persistence.*

@Entity
@Table(name = "parent")
@NamedEntityGraph(
    name = "parent-entity-graph",
    attributeNodes = [
        NamedAttributeNode("children")
    ],
)
data class Parent(
    @Id
    val id: Long,

    val name: String,

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parent")
    val children: MutableList<Child> = mutableListOf()
) {
    constructor() : this(-1, "")
}