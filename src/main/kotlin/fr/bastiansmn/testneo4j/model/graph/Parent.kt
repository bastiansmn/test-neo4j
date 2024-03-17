package fr.bastiansmn.testneo4j.model.graph

import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship

@Node("Parent")
data class Parent(

    @Id
    var id: Long,

    var name: String,

    @Relationship(type = "HAS_CHILD", direction = Relationship.Direction.OUTGOING)
    var children: MutableList<Child> = mutableListOf()
)
