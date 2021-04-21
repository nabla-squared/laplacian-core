package laplacian.metamodel


import laplacian.util.*

/**
 * An entity describing a value_domain.
 */
interface ValueDomain {
    /**
     * Allowed pattern in Regular expression.
     */
    val pattern: String?
    /**
     * The list of allowed values.
     */
    val choices: List<ValueItem>
    /**
     * Returns wether this instance is a value_domain or not.
     */
    val isaValueDomain: Boolean
}
