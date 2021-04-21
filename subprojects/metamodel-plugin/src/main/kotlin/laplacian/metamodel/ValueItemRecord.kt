package laplacian.metamodel
import com.github.jknack.handlebars.Context



import laplacian.generate.util.*
/**
 * An entity describing a value_item.
 */
data class ValueItemRecord (
    private val __record: Record,
    private val _context: Context,
    private val _record: Record = __record.normalizeCamelcase()
): ValueItem, Record by _record {

    /**
     * The value of this value_item.
     */
    override val value: String
        get() = getOrThrow("value")
    /**
     * The label of this value_item.
     */
    override val label: String
        get() = getOrThrow("label") {
            value
        }
    /**
     * The description of this value_item.
     */
    override val description: String
        get() = getOrThrow("description") {
            "label (=$value)"
        }
    /**
     * Returns wether this instance is a value_item or not.
     */
    override val isaValueItem: Boolean = true

    companion object {
        /**
         * Creates record list from list of map.
         */
        fun from(records: RecordList, _context: Context) = records.map { from(it, _context) }

        fun from(record: Record, _context: Context) =
            ValueItemRecord(record, _context)
    }
}
