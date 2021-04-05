package laplacian.metamodel
import com.github.jknack.handlebars.Context
import laplacian.metamodel.Relationship

import laplacian.metamodel.Property




import laplacian.generate.util.*
/**
 * An entity describing a property_mapping.
 */
data class PropertyMappingRecord (
    private val __record: Record,
    private val _context: Context,
    override val relationship: Relationship,
    private val _record: Record = __record.normalizeCamelcase()
): PropertyMapping, Record by _record {

    /**
     * The from of this property_mapping.
     */
    override val from: String
        get() = getOrThrow("from")
    /**
     * The to of this property_mapping.
     */
    override val to: String
        get() = getOrThrow("to")
    /**
     * The null_value of this property_mapping.
     */
    override val nullValue: String?
        by _record
    /**
     * The property of this property_mapping.
     */
    override val property: Property by lazy {
        relationship.entity.properties.find{ it.name == from }!!
    }
    /**
     * The reference_property of this property_mapping.
     */
    override val referenceProperty: Property by lazy {
        relationship.referenceEntity.properties.find{ it.name == to }!!
    }
    /**
     * Returns wether this instance is a property_mapping or not.
     */
    override val isaPropertyMapping: Boolean = true

    companion object {
        /**
         * Creates record list from list of map.
         */
        fun from(records: RecordList, _context: Context, relationship: Relationship) = records
            .map { from(it, _context, relationship = relationship) }

        fun from(record: Record, _context: Context, relationship: Relationship) =
            PropertyMappingRecord(record, _context, relationship = relationship)
    }
}
