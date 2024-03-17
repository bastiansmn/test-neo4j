package fr.bastiansmn.testneo4j.mapper

import fr.bastiansmn.testneo4j.dto.ChildDto
import fr.bastiansmn.testneo4j.model.graph.Child
import fr.bastiansmn.testneo4j.model.relationnal.Parent

class ChildMapper {
    companion object {
        fun graphToDto(child: Child): ChildDto {
            return ChildDto(
                id = child.id,
                name = child.name
            )
        }

        fun graphToDtoList(children: List<Child>): List<ChildDto> {
            return children.map { graphToDto(it) }
        }

        fun relationnalToDto(child: fr.bastiansmn.testneo4j.model.relationnal.Child): ChildDto {
            return ChildDto(
                id = child.id,
                name = child.name
            )
        }

        fun relationnalToDtoList(children: List<fr.bastiansmn.testneo4j.model.relationnal.Child>): List<ChildDto> {
            return children.map { relationnalToDto(it) }
        }

        fun relationalParentToChildDto(parent: Parent): List<ChildDto> {
            return parent.children.map { relationnalToDto(it) }
        }

        fun relationnalParentToDtoList(children: List<Parent>): List<ChildDto> {
            return children.flatMap { relationalParentToChildDto(it) }
        }
    }
}