package fr.bastiansmn.testneo4j.model.relationnal

import jakarta.persistence.*

@Entity
@Table(name = "child")
data class Child(
    @Id
    var id: Long,

    var name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    var parent: Parent
) {
    constructor() : this(-1, "", Parent(-1, ""))
}
