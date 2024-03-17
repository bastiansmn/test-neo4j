package fr.bastiansmn.testneo4j.service

import fr.bastiansmn.testneo4j.dto.graph.ParentChildDto
import fr.bastiansmn.testneo4j.model.relationnal.Parent
import fr.bastiansmn.testneo4j.repository.ParentJPARepository
import fr.bastiansmn.testneo4j.repository.ParentNeo4jRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class ParentService(
    private val parentNeo4jRepository: ParentNeo4jRepository,
    private val parentRelationalRepository: ParentJPARepository,
    private val parentRelationalSpecification: ParentSpecification
) {

    fun getGraphParentEntity(): List<ParentChildDto> {
        return parentNeo4jRepository.findParentsWithChildren("Tim")
    }

    fun getRelationnalParentEntity(filter: String): List<Parent> {
        return parentRelationalRepository.findParentsWithChildrenWithoutJoinFetch(filter)
    }

    fun getRelationnalWithSpecParentEntity(filter: String): List<Parent> {
        val parentSpecification = parentRelationalSpecification.hasChildrenWithName(filter)

        return parentRelationalRepository.findAll(parentSpecification)
    }

}