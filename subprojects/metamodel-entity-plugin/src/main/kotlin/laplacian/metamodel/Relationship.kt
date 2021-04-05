package laplacian.metamodel


import laplacian.util.*

/**
 * An entity describing a relationship.
 */
interface Relationship {
    /**
     * The name of this relationship.
     */
    val name: String
    /**
     * The identifier of this relationship.
     */
    val identifier: String
    /**
     * The cardinality of this relationship.
     */
    val cardinality: String
    /**
     * The reference_entity_name of this relationship.
     */
    val referenceEntityName: String
    /**
     * The reference_entity_namespace of this relationship.
     */
    val referenceEntityNamespace: String
    /**
     * Defines this relationship is aggregate or not.
     */
    val aggregate: Boolean
    /**
     * Defines this relationship is inherited or not.
     */
    val inherited: Boolean
    /**
     * The reverse_of of this relationship.
     */
    val reverseOf: String?
    /**
     * The description of this relationship.
     */
    val description: String
    /**
     * The snippet of this relationship.
     */
    val snippet: String?
    /**
     * The class_name of this relationship.
     */
    val className: String
    /**
     * Defines this relationship is multiple or not.
     */
    val multiple: Boolean
    /**
     * Defines this relationship is allows_empty or not.
     */
    val allowsEmpty: Boolean
    /**
     * Defines this relationship is nullable or not.
     */
    val nullable: Boolean
    /**
     * The property_name of this relationship.
     */
    val propertyName: String
    /**
     * Defines this relationship is bidirectional or not.
     */
    val bidirectional: Boolean
    /**
     * Defines this relationship is recursive or not.
     */
    val recursive: Boolean
    /**
     * Defines this relationship is deprecated or not.
     */
    val deprecated: Boolean
    /**
     * The examples of this relationship.
     */
    val examples: List<String>
    /**
     * The entity of this relationship.
     */
    val entity: Entity
    /**
     * The mappings of this relationship.
     */
    val mappings: List<PropertyMapping>
    /**
     * The reference_entity of this relationship.
     */
    val referenceEntity: Entity
    /**
     * The reverse of this relationship.
     */
    val reverse: Relationship?
    /**
     * Returns wether this instance is a relationship or not.
     */
    val isaRelationship: Boolean
}
