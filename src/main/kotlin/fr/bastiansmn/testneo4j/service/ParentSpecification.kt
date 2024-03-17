package fr.bastiansmn.testneo4j.service

import fr.bastiansmn.testneo4j.model.relationnal.Child
import fr.bastiansmn.testneo4j.model.relationnal.Parent
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component

@Component
class ParentSpecification {

    fun hasChildrenWithName(name: String): Specification<Parent> {
        return Specification { root, query, cb ->
            query.distinct(true)
            val join = root.join<Parent, Child>("children")
            val condition = cb.like(
                cb.upper(join.get("name")),
                cb.upper(cb.literal("%$name%"))
            )
            cb.and(condition)
        }
    }

}