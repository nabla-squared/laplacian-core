package laplacian.metamodel
import com.github.jknack.handlebars.Context
import laplacian.metamodel.ValueItem




import laplacian.generate.util.*
/**
 * An entity describing a value_domain.
 */
data class ValueDomainRecord (
    private val __record: Record,
    private val _context: Context,
    private val _record: Record = __record.normalizeCamelcase()
): ValueDomain, Record by _record {

    /**
     * Allowed pattern in Regular expression.
     */
    override val pattern: String?
        by _record
    /**
     * The list of allowed values.
     */
    override val choices: List<ValueItem> by lazy {
        ValueItemRecord.from(_record.getList("choices", emptyList()), _context)
    }
    /**
     * Returns wether this instance is a value_domain or not.
     */
    override val isaValueDomain: Boolean = true

    companion object {
        /**
         * Creates record list from list of map.
         */
        fun from(records: RecordList, _context: Context) = records.map { from(it, _context) }

        fun from(record: Record, _context: Context) =
            ValueDomainRecord(record, _context)
    }
}
