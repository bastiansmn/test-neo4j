# Notes pour CR

## 1. JPA et fetch des entités

Rappel de problématique : 
Soit une entité A qui contient une liste de B. Comment charger toutes les entités A selon une condition sur B et donc ne ramener que les B correspondants en une seule requête.

```kotlin
@Query("""
    SELECT p
    FROM Parent p
    JOIN p.children c
    WHERE UPPER(c.name) LIKE UPPER(CONCAT('%', :name, '%'))
""")
fun findParentsWithChildren(name: String): List<Parent>
```

Le problème ici, c'est que même avec un LAZY ou EAGER sur parent.children, il y a deux requêtes exécutées par JPA. Une pour les parents et une pour les enfants.

```
Hibernate: select p1_0.id,p1_0.name from parent p1_0 join child c1_0 on p1_0.id=c1_0.parent_id where upper(c1_0.name) like upper(('%'||?||'%')) escape ''
Hibernate: select c1_0.parent_id,c1_0.id,c1_0.name from child c1_0 where c1_0.parent_id=?
```

En plus, toutes les entités B (Child) sont chargées, même si on a demandé à filtrer sur un attribut de B.

### Solution 1 : JOIN FETCH

```kotlin
@Query("""
    SELECT p
    FROM Parent p
    JOIN FETCH p.children c
    WHERE UPPER(c.name) LIKE UPPER(CONCAT('%', :name, '%'))
""")
fun findParentsWithChildren(name: String): List<Parent>

@Entity
@Table(name = "parent")
data class Parent(
    @Id
    val id: Long,

    val name: String,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    val children: MutableList<Child> = mutableListOf()
) {
    constructor() : this(-1, "")
}
```

Ici, on utilise JOIN FETCH pour charger les enfants en même temps que les parents. À noter que JOIN FETCH prend le dessus sur LAZY, c'est-à-dire que même si plus tard dans le code, un élément (un mapper par exemple) fait `parent.children`, aucune requête ne sera faite. Une seule requête est exécutée ici :

```
Hibernate: select p1_0.id,c1_0.parent_id,c1_0.id,c1_0.name,p1_0.name from parent p1_0 join child c1_0 on p1_0.id=c1_0.parent_id where upper(c1_0.name) like upper(('%'||?||'%')) escape ''
```

Le problème est qu'il est difficile (est-ce impossible ??) d'intégrer ceci dans une spécification JPA. Donc la solution est limitée à des requêtes JPQL.

### Solution 2 : EntityGraph

```kotlin
@EntityGraph(value = "parent-entity-graph")
@Query("""
    SELECT p
    FROM Parent p
    JOIN p.children c
    WHERE UPPER(c.name) LIKE UPPER(CONCAT('%', :name, '%'))
    """)
fun findParentsWithChildren(name: String): List<Parent>

@Entity
@Table(name = "parent")
@NamedEntityGraph(
    name = "parent-entity-graph",
    attributeNodes = [
        NamedAttributeNode("children")
    ],
)
data class Parent(
    @Id
    val id: Long,

    val name: String,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    val children: MutableList<Child> = mutableListOf()
) {
    constructor() : this(-1, "")
}
```

Ici, on spécifie un NamedEntityGraph qui va charger les enfants en même temps que les parents. On peut spécifier plusieurs NamedAttributeNode pour charger plusieurs relations en même temps.

Une seule requête est exécutée ici :

```
Hibernate: select p1_0.id,c1_0.parent_id,c1_0.id,c1_0.name,p1_0.name from parent p1_0 join child c1_0 on p1_0.id=c1_0.parent_id where upper(c1_0.name) like upper(('%'||?||'%')) escape ''
```

Il est possible de définir un `EntityGraph` sur le repository directement au lieu de le spécifier de manière générale sur l'entité.

Cette fois-ci, il est possible de l'intégrer dans une spécification JPA. Le tour ici est d'ajouter une surcharge de la méthode `findAll` dans le repository parent.

```kotlin
@EntityGraph(value = "parent-entity-graph")
override fun findAll(specification: Specification<Parent>): List<Parent>
```

Et à l'utilisation :

```kotlin
// Code du service
fun getRelationnalWithSpecParentEntity(filter: String): List<Parent> {
    val parentSpecification = parentRelationalSpecification.hasChildrenWithName(filter)

    return parentRelationalRepository.findAll(parentSpecification)
}

// Code de la spécification
fun hasChildrenWithName(name: String): Specification<Parent> {
    return Specification { root, query, cb ->
        val join = root.join<Parent, Child>("children")
        val condition = cb.like(
            cb.upper(join.get("name")),
            cb.upper(cb.literal("%$name%"))
        )
        cb.and(condition)
    }
}
```

La requête exécutée ici est la même que précédemment.

```
Hibernate: select p1_0.id,c1_0.parent_id,c1_0.id,c1_0.name,p1_0.name from parent p1_0 join child c1_0 on p1_0.id=c1_0.parent_id where upper(c1_0.name) like upper(('%'||?||'%')) escape ''
```
