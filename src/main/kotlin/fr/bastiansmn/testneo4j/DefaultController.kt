package fr.bastiansmn.testneo4j

import fr.bastiansmn.testneo4j.dto.ChildDto
import fr.bastiansmn.testneo4j.dto.ParentDto
import fr.bastiansmn.testneo4j.mapper.ChildMapper
import fr.bastiansmn.testneo4j.mapper.ParentMapper
import fr.bastiansmn.testneo4j.service.ParentService
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/default")
@RequiredArgsConstructor
class DefaultController(
    private val parentService: ParentService
) {

    @GetMapping("graph")
    fun getParentEntity(): ResponseEntity<List<ParentDto>> {
        return ResponseEntity
            .ok(
                ParentMapper.graphToDtoList(parentService.getGraphParentEntity())
            )
    }

    @GetMapping("relational")
    fun getRelationnalParentEntity(
        @RequestParam("filter") filter: String
    ): ResponseEntity<List<ParentDto>> {
        return ResponseEntity
            .ok(
                ParentMapper.relationnalToDtoList(parentService.getRelationnalParentEntity(filter))
            )
    }

    @GetMapping("relational/child")
    fun getRelationnalChildrenEntity(
        @RequestParam("filter") filter: String
    ): ResponseEntity<List<ChildDto>> {
        return ResponseEntity
            .ok(
                ChildMapper.relationnalParentToDtoList(parentService.getRelationalChildrenOfParent(filter))
            )
    }

    @GetMapping("relational-with-spec")
    fun getRelationnalWithSpecParentEntity(
        @RequestParam("filter") filter: String
    ): ResponseEntity<List<ParentDto>> {
        return ResponseEntity
            .ok(
                ParentMapper.relationnalToDtoList(parentService.getRelationnalWithSpecParentEntity(filter))
            )
    }

}