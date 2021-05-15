package laplacian.metamodel


import laplacian.util.*

/**
 * An entity describing a property.
 */
interface Property {
    /**
     * The name of this property.
     */
    val name: String
    /**
     * The identifier of this property.
     */
    val identifier: String
    /**
     * Defines this property is primary_key or not.
     */
    val primaryKey: Boolean
    /**
     * Defines this property is subtype_key or not.
     */
    val subtypeKey: Boolean
    /**
     * The type of this property.
     */
    val type: String
    /**
     * The name of predefined value domain type for this property.
     */
    val domainTypeName: String?
    /**
     * The maximum allowed size of the content of this property.
     */
    val size: Int
    /**
     * Defines this property is optional or not.
     */
    val optional: Boolean
    /**
     * The description of this property.
     */
    val description: String
    /**
     * The default value of this property, which is used when the actual value is null

     */
    val defaultValue: String?
    /**
     * The tags of this property.
     */
    val tags: List<String>
    /**
     * Kotlin expressions which represent some typical values of this property.

     */
    val exampleValues: List<String>
    /**
     * A kotlin expression which represents a typical value of this property.

     */
    val exampleValue: String
    /**
     * The table_column_name of this property.
     */
    val tableColumnName: String
    /**
     * The snippet of this property.
     */
    val snippet: String?
    /**
     * Defines this property is multiple or not.
     */
    val multiple: Boolean
    /**
     * The property_name of this property.
     */
    val propertyName: String
    /**
     * The class_name of this property.
     */
    val className: String
    /**
     * The class_name_in_kotlin of this property.
     */
    val classNameInKotlin: String
    /**
     * The class_name_in_java of this property.
     */
    val classNameInJava: String
    /**
     * Whether this property permits a null value

     */
    val nullable: Boolean
    /**
     * Whether this preoperty overwrite a property which is defined in supertype.

     */
    val overwrites: Boolean
    /**
     * Defines this property is deprecated or not.
     */
    val deprecated: Boolean
    /**
     * The entity of this property.
     */
    val entity: Entity
    /**
     * The domain of this property.
     */
    val domain: ValueDomain?
    /**
     * The domain_type of this property.
     */
    val domainType: ValueDomainType?
    /**
     * Returns wether this instance is a property or not.
     */
    val isaProperty: Boolean
}
