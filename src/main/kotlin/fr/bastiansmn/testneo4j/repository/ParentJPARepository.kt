package fr.bastiansmn.testneo4j.repository

import fr.bastiansmn.testneo4j.model.relationnal.Parent
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ParentJPARepository: JpaRepository<Parent, Long>, JpaSpecificationExecutor<Parent> {

    @EntityGraph(value = "parent-entity-graph")
    override fun findAll(specification: Specification<Parent>): List<Parent>

    @Query("""
        SELECT p
        FROM Parent p
        JOIN FETCH p.children c
        WHERE UPPER(c.name) LIKE UPPER(CONCAT('%', :name, '%'))
        """)
    fun findParentsWithChildren(name: String): List<Parent>

    @Query("""
        SELECT p
        FROM Parent p
        JOIN p.children c
        WHERE UPPER(c.name) LIKE UPPER(CONCAT('%', :name, '%'))
        """)
    fun findParentsWithChildrenWithoutJoinFetch(name: String): List<Parent>

    @EntityGraph(value = "parent-entity-graph")
    @Query("""
        SELECT p
        FROM Parent p
        JOIN p.children c
        WHERE UPPER(c.name) LIKE UPPER(CONCAT('%', :name, '%'))
        """)
    fun findParentsWithChildrenWithEntityGraph(name: String): List<Parent>

    @Query("""
        SELECT p
        FROM Parent p
        JOIN p.children c
        WHERE UPPER(c.name) LIKE UPPER(CONCAT('%', :name, '%'))
        """)
    fun findParentsWithChildrenWithoutEntityGraph(name: String): List<Parent>

}