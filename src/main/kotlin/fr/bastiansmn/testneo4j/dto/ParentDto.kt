package fr.bastiansmn.testneo4j.dto

data class ParentDto(
    val id: Long?,
    val name: String?,
    val children: List<ChildDto>
)
