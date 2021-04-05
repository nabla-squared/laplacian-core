package laplacian.metamodel


import laplacian.util.*

/**
 * An entity describing a value_domain_type.
 */
interface ValueDomainType {
    /**
     * The name of this value_domain_type.
     */
    val name: String
    /**
     * The namespace of this value_domain_type.
     */
    val namespace: String
    /**
     * The type of this value_domain_type.
     */
    val type: String
    /**
     * The description of this value_domain_type.
     */
    val description: String
    /**
     * The domain of this value_domain_type.
     */
    val domain: ValueDomain
    /**
     * Returns wether this instance is a value_domain_type or not.
     */
    val isaValueDomainType: Boolean
}
