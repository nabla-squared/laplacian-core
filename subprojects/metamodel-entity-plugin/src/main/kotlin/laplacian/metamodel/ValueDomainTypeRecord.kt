package laplacian.metamodel
import com.github.jknack.handlebars.Context
import laplacian.metamodel.ValueDomain




import laplacian.generate.util.*
/**
 * An entity describing a value_domain_type.
 */
data class ValueDomainTypeRecord (
    private val __record: Record,
    private val _context: Context,
    private val _record: Record = __record.normalizeCamelcase()
): ValueDomainType, Record by _record {

    /**
     * The name of this value_domain_type.
     */
    override val name: String
        get() = getOrThrow("name")
    /**
     * The namespace of this value_domain_type.
     */
    override val namespace: String
        get() = getOrThrow("namespace")
    /**
     * The type of this value_domain_type.
     */
    override val type: String
        get() = getOrThrow("type")
    /**
     * The description of this value_domain_type.
     */
    override val description: String
        get() = getOrThrow("description") {
            name
        }
    /**
     * The domain of this value_domain_type.
     */
    override val domain: ValueDomain by lazy {
        ValueDomainRecord(getOrThrow<Record>("domain"), _context)
    }
    /**
     * Returns wether this instance is a value_domain_type or not.
     */
    override val isaValueDomainType: Boolean = true

    companion object {
        /**
         * Creates record list from list of map.
         */
        fun from(_context: Context): AllValueDomainTypes {
            return _context.get("value_domain_types") as AllValueDomainTypes
        }
        fun from(records: RecordList, _context: Context) = records.map { from(it, _context) }

        fun from(record: Record, _context: Context) =
            ValueDomainTypeRecord(record, _context)
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ValueDomainTypeRecord) return false
        if (name != other.name) return false
        if (namespace != other.namespace) return false
        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + namespace.hashCode()
        return result
    }

    override fun toString(): String {
        return "ValueDomainTypeRecord(" +
            "name=$name, " +
            "namespace=$namespace" +
        ")"
    }
}
