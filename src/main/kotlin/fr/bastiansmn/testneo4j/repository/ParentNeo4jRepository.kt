package fr.bastiansmn.testneo4j.repository

import fr.bastiansmn.testneo4j.dto.graph.ParentChildDto
import fr.bastiansmn.testneo4j.model.graph.Parent
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.stereotype.Repository

@Repository
interface ParentNeo4jRepository: Neo4jRepository<Parent, Long> {

    @Query("""
        MATCH (p:Parent)-[:HAS_CHILD]->(c:Child)
        WHERE c.name =~ 'Tim.*'
        RETURN p as parent, collect(c) AS children
    """)
    fun findParentsWithChildren(name: String): List<ParentChildDto>

}