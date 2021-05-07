package laplacian.metamodel
import com.github.jknack.handlebars.Context
import laplacian.metamodel.Entity

import laplacian.metamodel.ValueDomain

import laplacian.metamodel.ValueDomainType




import laplacian.generate.util.*
/**
 * An entity describing a property.
 */
data class PropertyRecord (
    private val __record: Record,
    private val _context: Context,
    override val entity: Entity,
    private val _record: Record = __record.normalizeCamelcase()
): Property, Record by _record {

    /**
     * The name of this property.
     */
    override val name: String
        get() = getOrThrow("name")
    /**
     * The identifier of this property.
     */
    override val identifier: String
        get() = getOrThrow("identifier") {
            name.lowerUnderscorize()
        }
    /**
     * Defines this property is primary_key or not.
     */
    override val primaryKey: Boolean
        get() = getOrThrow("primaryKey") {
            false
        }
    /**
     * Defines this property is subtype_key or not.
     */
    override val subtypeKey: Boolean
        get() = getOrThrow("subtypeKey") {
            false
        }
    /**
     * The type of this property.
     */
    override val type: String
        get() = getOrThrow("type")
    /**
     * The name of predefined value domain type for this property.
     */
    override val domainTypeName: String?
        by _record
    /**
     * The maximum allowed size of the content of this property.
     */
    override val size: Int
        get() = getOrThrow("size") {
            when(type) {
                "string" -> 200
                else -> 0
            }
        }
    /**
     * Defines this property is optional or not.
     */
    override val optional: Boolean
        get() = getOrThrow("optional") {
            false
        }
    /**
     * The description of this property.
     */
    override val description: String
        get() = getOrThrow("description") {
            if (type == "boolean") "Defines this ${entity.name} is $name or not." else "The $name of this ${entity.name}."
        }
    /**
     * The default value of this property, which is used when the actual value is null
     */
    override val defaultValue: String?
        by _record
    /**
     * The tags of this property.
     */
    override val tags: List<String>
        get() = getOrThrow("tags") {
            emptyList<String>()
        }
    /**
     * Kotlin expressions which represent some typical values of this property.
     */
    override val exampleValues: List<String>
        get() = getOrThrow("exampleValues") {
            emptyList<String>()
        }
    /**
     * A kotlin expression which represents a typical value of this property.
     */
    override val exampleValue: String
        get() = when {
          !exampleValues.isEmpty() -> exampleValues.first()
          (defaultValue != null) -> defaultValue.toString()
          multiple -> "emptyList<${className}>()"
          (type == "string") -> "\"hogehoge\""
          (type == "number") -> "42"
          else -> "null"
        }
    /**
     * The table_column_name of this property.
     */
    override val tableColumnName: String
        get() = getOrThrow("tableColumnName") {
            identifier.lowerUnderscorize()
        }
    /**
     * The snippet of this property.
     */
    override val snippet: String?
        by _record
    /**
     * Defines this property is multiple or not.
     */
    override val multiple: Boolean
        get() = getOrThrow("multiple") {
            false
        }
    /**
     * The property_name of this property.
     */
    override val propertyName: String
        get() = identifier.lowerCamelize()
    /**
     * The class_name of this property.
     */
    override val className: String
        get() = when(type) {
            "number" -> "Int"
            "date", "datetime", "time" -> "String"
            "object", "any_entity" -> "Any"
            else -> type.upperCamelize()
        }
        .let {
            if (multiple) "List<$it>" else it
        }
    /**
     * Whether this property permits a null value
     */
    override val nullable: Boolean
        get() = optional && !multiple && (defaultValue == null)
    /**
     * Whether this preoperty overwrite a property which is defined in supertype.
     */
    override val overwrites: Boolean
        get() = entity.ancestors.any{ s -> s.properties.any{ p -> p.name == name }}
    /**
     * Defines this property is deprecated or not.
     */
    override val deprecated: Boolean
        get() = getOrThrow("deprecated") {
            false
        }
    /**
     * The domain of this property.
     */
    override val domain: ValueDomain? by lazy {
        getOrNull<Record>("domain")?.let{ ValueDomainRecord.from(it, _context) }
    }
    /**
     * The domain_type of this property.
     */
    override val domainType: ValueDomainType? by lazy {
        ValueDomainTypeRecord.from(_context).find {
            it.name == domainTypeName
        }
    }
    /**
     * Returns wether this instance is a property or not.
     */
    override val isaProperty: Boolean = true

    companion object {
        /**
         * Creates record list from list of map.
         */
        fun from(records: RecordList, _context: Context, entity: Entity) = records
            .mergeWithKeys("name")
            .map { from(it, _context, entity = entity) }

        fun from(record: Record, _context: Context, entity: Entity) =
            PropertyRecord(record, _context, entity = entity)
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PropertyRecord) return false
        if (entity != other.entity) return false
        if (name != other.name) return false
        return true
    }

    override fun hashCode(): Int {
        var result = entity.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }

    override fun toString(): String {
        return "PropertyRecord(" +
            "entity=$entity, " +
            "name=$name" +
        ")"
    }
}
