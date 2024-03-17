package fr.bastiansmn.testneo4j.repository

import fr.bastiansmn.testneo4j.model.relationnal.Parent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ParentJPARepository: JpaRepository<Parent, Long>, JpaSpecificationExecutor<Parent> {

    @Query("""
        SELECT p
        FROM Parent p
        JOIN FETCH p.children c
        WHERE UPPER(c.name) LIKE UPPER(CONCAT('%', :name, '%'))
        """)
    fun findParentsWithChildren(name: String): List<Parent>

}