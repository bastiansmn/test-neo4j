CREATE
(p:Parent {id: 1, name: "Bastian"}),
(c1:Child {id: 1, name: "Timothé"}),
(c2:Child {id: 2, name: "Toto"}),
(c3:Child {id: 3, name: "Tim"})

CREATE
  (p)-[:HAS_CHILD]->(c1),
  (p)-[:HAS_CHILD]->(c2),
  (p)-[:HAS_CHILD]->(c3)
