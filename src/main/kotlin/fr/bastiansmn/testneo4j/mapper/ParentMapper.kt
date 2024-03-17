package fr.bastiansmn.testneo4j.mapper

import fr.bastiansmn.testneo4j.dto.ParentDto
import fr.bastiansmn.testneo4j.dto.graph.ParentChildDto
import fr.bastiansmn.testneo4j.model.relationnal.Parent

class ParentMapper {
    companion object {
        fun graphToDto(parent: ParentChildDto): ParentDto {
            return ParentDto(
                id = parent.parent.id,
                name = parent.parent.name,
                children = parent.children
            )
        }

        fun graphToDtoList(parents: List<ParentChildDto>): List<ParentDto> {
            return parents.map { graphToDto(it) }
        }

        fun relationnalToDto(parent: Parent): ParentDto {
            return ParentDto(
                id = parent.id,
                name = parent.name,
                children = ChildMapper.relationnalToDtoList(parent.children)
            )
        }

        fun relationnalToDtoList(parents: List<Parent>): List<ParentDto> {
            return parents.map { relationnalToDto(it) }
        }
    }
}