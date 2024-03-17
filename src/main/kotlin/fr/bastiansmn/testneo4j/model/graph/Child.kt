package fr.bastiansmn.testneo4j.model.graph

import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node

@Node("Child")
data class Child(

    @Id
    var id: Long,

    var name: String,

)
