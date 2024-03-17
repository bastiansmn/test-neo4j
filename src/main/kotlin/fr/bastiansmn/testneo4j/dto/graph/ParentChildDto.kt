package fr.bastiansmn.testneo4j.dto.graph

import fr.bastiansmn.testneo4j.dto.ChildDto
import fr.bastiansmn.testneo4j.model.graph.Parent

data class ParentChildDto(
    var parent: Parent,
    var children: List<ChildDto>
)
