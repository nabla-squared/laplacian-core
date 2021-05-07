package laplacian.metamodel


import laplacian.util.*

/**
 * An entity describing a entity.
 */
interface Entity {
    /**
     * The name of this entity.
     */
    val name: String
    /**
     * The namespace of this entity.
     */
    val namespace: String
    /**
     * The tags of this entity.
     */
    val tags: List<String>
    /**
     * The identifier of this entity.
     */
    val identifier: String
    /**
     * If this property is true, there is the "root" instance, which is accessible globally.

     */
    val singlyRooted: Boolean
    /**
     * The description of this entity.
     */
    val description: String
    /**
     * Defines this entity is value_object or not.
     */
    val valueObject: Boolean
    /**
     * The class_name of this entity.
     */
    val className: String
    /**
     * The table_name of this entity.
     */
    val tableName: String
    /**
     * The supertype_name of this entity.
     */
    val supertypeName: String?
    /**
     * The supertype_namespace of this entity.
     */
    val supertypeNamespace: String
    /**
     * The value of subtype key that represents this type of entity,
which is used when implementing polymorphism. The name of entity is used by default.

     */
    val subtypeKeyValue: String
    /**
     * Deprecated: prefer to use the 'reverse_of' property

     */
    val inherited: Boolean
    /**
     * Defines this entity is top_level or not.
     */
    val topLevel: Boolean
    /**
     * Defines this entity is supports_namespace or not.
     */
    val supportsNamespace: Boolean
    /**
     * The fqn of this entity.
     */
    val fqn: String
    /**
     * The primary_key_names of this entity.
     */
    val primaryKeyNames: List<String>
    /**
     * Defines this entity is deprecated or not.
     */
    val deprecated: Boolean
    /**
     * examples which explain actual usage of this entity
     */
    val examples: List<String>
    /**
     * The properties of this entity (excluding supertypes')
     */
    val properties: List<Property>
    /**
     * The properties of this entity
     */
    val allProperties: List<Property>
    /**
     * The relationships with other entities (excluding supertypes')
     */
    val relationships: List<Relationship>
    /**
     * The relationships including supertype's ones.
     */
    val allRelationships: List<Relationship>
    /**
     * The entity which this entity is subtype of
     */
    val supertype: Entity?
    /**
     * The root entity of the inheritance tree including this entity.

     */
    val root: Entity
    /**
     * The entities which are supertype of this entity (recursive).

     */
    val ancestors: List<Entity>
    /**
     * The subtype entities of this entity
     */
    val subtypes: List<Entity>
    /**
     * All the subtypes of this entity
     */
    val descendants: List<Entity>
    /**
     * The property which is used to identify the type of a entity.
     */
    val subtypeKey: Property?
    /**
     * このエンティティに対するルートクエリ
     */
    val queries: List<Query>
    /**
     * 一意識別キーとなるプロパティのリスト
     */
    val primaryKeys: List<Property>
    /**
     * このエンティティの導出元エンティティ このエンティティが導出エンティティでなければ空集合

     */
    val inheritedFrom: List<Relationship>
    /**
     * The owned_by of this entity.
     */
    val ownedBy: Relationship?
    /**
     * The relationship expresses the ownership of this entity

     */
    val ownership: Relationship?
    /**
     * The entity this entity owns
     */
    val owner: Entity?
    /**
     * The aggregation tree this entity is owned

     */
    val ownershipHierarchy: List<Relationship>
    /**
     * The root_owner of this entity.
     */
    val rootOwner: Entity?
    /**
     * このエンティティが参照するエンティティの一覧(自身は除く)

     */
    val relatingEntities: List<Entity>
    /**
     * このエンティティが参照するトップレベルエンティティの一覧(自身は除く)
     */
    val relatingTopLevelEntities: List<Entity>
    /**
     * このエンティティが参照する外部パッケージのエンティティ
     */
    val relatingExternalEntities: List<Entity>
    /**
     * このエンティティが管理する集約
     */
    val aggregates: List<Relationship>
    /**
     * aggregates owned by this entity or its ancestors
     */
    val allAggregates: List<Relationship>
    /**
     * このエンティティに集約されているエンティティの一覧 (再帰的に集約されているものを含む)

     */
    val aggregatedEntities: List<Entity>
    /**
     * このエンティティが直接値を保持するプロパティ
     */
    val storedProperties: List<Property>
    /**
     * このエンティティが直接関連値を保持している関連
     */
    val storedRelationships: List<Relationship>
    /**
     * Returns wether this instance is a entity or not.
     */
    val isaEntity: Boolean
}
