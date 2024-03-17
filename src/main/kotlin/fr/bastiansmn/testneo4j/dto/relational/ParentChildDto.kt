package fr.bastiansmn.testneo4j.dto.relational

import fr.bastiansmn.testneo4j.dto.ChildDto
import fr.bastiansmn.testneo4j.model.relationnal.Parent

data class ParentChildDto(
    var parent: Parent,
    var children: List<ChildDto>
)
