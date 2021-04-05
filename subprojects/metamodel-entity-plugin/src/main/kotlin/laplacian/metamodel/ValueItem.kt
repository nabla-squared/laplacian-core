package laplacian.metamodel


import laplacian.util.*

/**
 * An entity describing a value_item.
 */
interface ValueItem {
    /**
     * The value of this value_item.
     */
    val value: String
    /**
     * The label of this value_item.
     */
    val label: String
    /**
     * The description of this value_item.
     */
    val description: String
    /**
     * Returns wether this instance is a value_item or not.
     */
    val isaValueItem: Boolean
}
