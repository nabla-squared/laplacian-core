package laplacian.metamodel


import laplacian.util.*

/**
 * An entity describing a property_mapping.
 */
interface PropertyMapping {
    /**
     * The from of this property_mapping.
     */
    val from: String
    /**
     * The to of this property_mapping.
     */
    val to: String
    /**
     * The null_value of this property_mapping.
     */
    val nullValue: String?
    /**
     * The relationship of this property_mapping.
     */
    val relationship: Relationship
    /**
     * The property of this property_mapping.
     */
    val property: Property
    /**
     * The reference_property of this property_mapping.
     */
    val referenceProperty: Property
    /**
     * Returns wether this instance is a property_mapping or not.
     */
    val isaPropertyMapping: Boolean
}
