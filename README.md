Gestion des classes - ORM
========================

## _But :_

Projet NetBeans - JEE - ORM pour gérer des classes de cours :

* **Transformation du projet de gestion des classes DAO :** [Gestion des classes - DAO](https://github.com/ScytAl3/Gestion_des_classes_DAO).

## _Principe :_

* Remplacement des DAO par l’ Object-Relational Mapping (ORM) pour gérer la correspondance entre les objets de l’application et les tables de la base de données.
* Mise en place des annotations dans les classes Objet : @Entity, @Table, @Id, @Column etc…
* Mise à jour des servlets en utilisant l’EntityManager pour rendre persistantes les données d’un bean entité.
* Implémentation du standard JPA avec EclipseLink.
* Interaction entre les beans et le bdd en utilisant l’EntityManagerFactory et le fichier persistence.xml ainsi que l’EntityManager et l’EntityTransaction.
