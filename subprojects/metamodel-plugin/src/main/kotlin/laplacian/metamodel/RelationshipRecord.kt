package laplacian.metamodel
import com.github.jknack.handlebars.Context
import laplacian.metamodel.Entity

import laplacian.metamodel.PropertyMapping





import laplacian.generate.util.*
/**
 * An entity describing a relationship.
 */
data class RelationshipRecord (
    private val __record: Record,
    private val _context: Context,
    override val entity: Entity,
    private val _record: Record = __record.normalizeCamelcase()
): Relationship, Record by _record {

    /**
     * The name of this relationship.
     */
    override val name: String
        get() = getOrThrow("name")
    /**
     * The identifier of this relationship.
     */
    override val identifier: String
        get() = getOrThrow("identifier") {
            name.lowerUnderscorize()
        }
    /**
     * The cardinality of this relationship.
     */
    override val cardinality: String
        get() = getOrThrow("cardinality")
    /**
     * The reference_entity_name of this relationship.
     */
    override val referenceEntityName: String
        get() = getOrThrow("referenceEntityName")
    /**
     * The reference_entity_namespace of this relationship.
     */
    override val referenceEntityNamespace: String
        get() = getOrThrow("referenceEntityNamespace") {
            entity.namespace
        }
    /**
     * Defines this relationship is aggregate or not.
     */
    override val aggregate: Boolean
        get() = getOrThrow("aggregate") {
            false
        }
    /**
     * Defines this relationship is inherited or not.
     */
    override val inherited: Boolean
        get() = getOrThrow("inherited") {
            reverseOf != null
        }
    /**
     * The reverse_of of this relationship.
     */
    override val reverseOf: String?
        by _record
    /**
     * The description of this relationship.
     */
    override val description: String
        get() = getOrThrow("description") {
            "The $name of this ${entity.name}."
        }
    /**
     * The snippet of this relationship.
     */
    override val snippet: String?
        by _record
    /**
     * The class_name of this relationship.
     */
    override val className: String
        get() = if (multiple) "List<${referenceEntity.className}>" else (referenceEntity.className + if (nullable) "?" else "")
    /**
     * Defines this relationship is multiple or not.
     */
    override val multiple: Boolean
        get() = cardinality.contains("""(\*|N|\.\.[2-9][0-9]+)""".toRegex())
    /**
     * Defines this relationship is allows_empty or not.
     */
    override val allowsEmpty: Boolean
        get() = cardinality == "N" || cardinality == "*" || cardinality.contains("""(0\.\.)""".toRegex())
    /**
     * Defines this relationship is nullable or not.
     */
    override val nullable: Boolean
        get() = !multiple && allowsEmpty
    /**
     * The property_name of this relationship.
     */
    override val propertyName: String
        get() = identifier.lowerCamelize()
    /**
     * Defines this relationship is bidirectional or not.
     */
    override val bidirectional: Boolean
        get() = aggregate && referenceEntity.relationships.any {
            it.inherited && (it.referenceEntity.fqn == this.entity.fqn)
        }
    /**
     * Defines this relationship is recursive or not.
     */
    override val recursive: Boolean
        get() = aggregate && (referenceEntity.fqn == entity.fqn)
    /**
     * Defines this relationship is deprecated or not.
     */
    override val deprecated: Boolean
        get() = getOrThrow("deprecated") {
            false
        }
    /**
     * The examples of this relationship.
     */
    override val examples: List<String>
        get() = getOrThrow("examples") {
            emptyList<String>()
        }
    /**
     * The mappings of this relationship.
     */
    override val mappings: List<PropertyMapping> by lazy {
        PropertyMappingRecord.from(_record.getList("mappings", emptyList()), _context, this)
    }
    /**
     * The reference_entity of this relationship.
     */
    override val referenceEntity: Entity by lazy {
        EntityRecord.from(_context).find {
            it.name == referenceEntityName &&
            it.namespace == referenceEntityNamespace
        } ?: throw IllegalStateException(
            "There is no entity which meets the following condition(s): "
            + "Relationship.reference_entity_name == entity.name (=$referenceEntityName) "
            + "Relationship.reference_entity_namespace == entity.namespace (=$referenceEntityNamespace) "
            + "Possible values are: " + EntityRecord.from(_context).map {
              "(${ it.name },${ it.namespace })"
            }.joinToString()
        )
    }
    /**
     * The reverse of this relationship.
     */
    override val reverse: Relationship? by lazy {
        referenceEntity.relationships.find{ it.name == reverseOf }
    }
    /**
     * Returns wether this instance is a relationship or not.
     */
    override val isaRelationship: Boolean = true

    companion object {
        /**
         * Creates record list from list of map.
         */
        fun from(records: RecordList, _context: Context, entity: Entity) = records
            .mergeWithKeys("name")
            .map { from(it, _context, entity = entity) }

        fun from(record: Record, _context: Context, entity: Entity) =
            RelationshipRecord(record, _context, entity = entity)
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RelationshipRecord) return false
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
        return "RelationshipRecord(" +
            "entity=$entity, " +
            "name=$name" +
        ")"
    }
}
