package laplacian.metamodel
import com.github.jknack.handlebars.Context
import laplacian.metamodel.Property

import laplacian.metamodel.Relationship


import laplacian.metamodel.Query




import laplacian.generate.util.*
/**
 * An entity describing a entity.
 */
data class EntityRecord (
    private val __record: Record,
    private val _context: Context,
    private val _record: Record = __record.normalizeCamelcase()
): Entity, Record by _record {

    /**
     * The name of this entity.
     */
    override val name: String
        get() = getOrThrow("name")
    /**
     * The namespace of this entity.
     */
    override val namespace: String
        get() = getOrThrow("namespace")
    /**
     * The tags of this entity.
     */
    override val tags: List<String>
        get() = getOrThrow("tags") {
            emptyList<String>()
        }
    /**
     * The identifier of this entity.
     */
    override val identifier: String
        get() = getOrThrow("identifier") {
            name.lowerUnderscorize()
        }
    /**
     * If this property is true, there is the "root" instance, which is accessible globally.
     */
    override val singlyRooted: Boolean
        get() = getOrThrow("singlyRooted") {
            false
        }
    /**
     * The description of this entity.
     */
    override val description: String
        get() = getOrThrow("description") {
            "An entity describing a ${name}."
        }
    /**
     * Defines this entity is value_object or not.
     */
    override val valueObject: Boolean
        get() = getOrThrow("valueObject") {
            false
        }
    /**
     * The class_name of this entity.
     */
    override val className: String
        get() = getOrThrow("className") {
            name.upperCamelize()
        }
    /**
     * The table_name of this entity.
     */
    override val tableName: String
        get() = getOrThrow("tableName") {
            "t_${name.lowerUnderscorize()}"
        }
    /**
     * The supertype_name of this entity.
     */
    override val supertypeName: String?
        by _record
    /**
     * The supertype_namespace of this entity.
     */
    override val supertypeNamespace: String
        get() = getOrThrow("supertypeNamespace") {
            namespace
        }
    /**
     * The value of subtype key that represents this type of entity,
which is used when implementing polymorphism. The name of entity is used by default.
     */
    override val subtypeKeyValue: String
        get() = getOrThrow("subtypeKeyValue") {
            name
        }
    /**
     * Deprecated: prefer to use the 'reverse_of' property
     */
    override val inherited: Boolean
        get() = supertype?.inherited ?: relationships.any{ it.inherited || it.reverse != null }
    /**
     * Defines this entity is top_level or not.
     */
    override val topLevel: Boolean
        get() = (owner == null || owner == this) && (supertype == null) && !valueObject
    /**
     * Defines this entity is supports_namespace or not.
     */
    override val supportsNamespace: Boolean
        get() = properties.any { p ->
            p.name == "namespace" && p.type == "string"
        }
    /**
     * The fqn of this entity.
     */
    override val fqn: String
        get() = "$namespace.$className"
    /**
     * The primary_key_names of this entity.
     */
    override val primaryKeyNames: List<String>
        get() = inheritedFrom.flatMap { inheritance ->
            inheritance.referenceEntity.primaryKeys.map { pk ->
                "${inheritance.identifier.lowerUnderscorize()}_${pk.propertyName.lowerUnderscorize()}"
            }
        } + primaryKeys.map { it.propertyName.lowerUnderscorize() }
    /**
     * Defines this entity is deprecated or not.
     */
    override val deprecated: Boolean
        get() = getOrThrow("deprecated") {
            false
        }
    /**
     * examples which explain actual usage of this entity
     */
    override val examples: List<String>
        get() = getOrThrow("examples") {
            emptyList<String>()
        }
    /**
     * The properties of this entity (excluding supertypes')
     */
    override val properties: List<Property> by lazy {
        PropertyRecord.from(_record.getList("properties", emptyList()), _context, this)
    }
    /**
     * The properties of this entity
     */
    override val allProperties: List<Property> by lazy {
        (supertype?.allProperties ?: emptyList()) + properties
    }
    /**
     * The relationships with other entities (excluding supertypes')
     */
    override val relationships: List<Relationship> by lazy {
        RelationshipRecord.from(_record.getList("relationships", emptyList()), _context, this)
    }
    /**
     * The relationships including supertype's ones.
     */
    override val allRelationships: List<Relationship> by lazy {
        (supertype?.allRelationships ?: emptyList()) + relationships
    }
    /**
     * The entity which this entity is subtype of
     */
    override val supertype: Entity? by lazy {
        EntityRecord.from(_context).find {
            it.name == supertypeName &&
            it.namespace == supertypeNamespace
        }
    }
    /**
     * The root entity of the inheritance tree including this entity.
     */
    override val root: Entity by lazy {
        if (supertype != null) ancestors.last() else this
    }
    /**
     * The entities which are supertype of this entity (recursive).
     */
    override val ancestors: List<Entity> by lazy {
        mutableListOf<Entity>().also {
            var ancestor = supertype
            while (ancestor != null) {
                it.add(ancestor)
                ancestor = ancestor.supertype
            }
        }
    }
    /**
     * The subtype entities of this entity
     */
    override val subtypes: List<Entity> by lazy {
        EntityRecord.from(_context).filter {
            it.supertypeName == name &&
            it.supertypeNamespace == namespace
        }
    }
    /**
     * All the subtypes of this entity
     */
    override val descendants: List<Entity> by lazy {
        subtypes + subtypes.flatMap{ it.descendants }
    }
    /**
     * The property which is used to identify the type of a entity.
     */
    override val subtypeKey: Property? by lazy {
        properties.find{ it.subtypeKey }
    }
    /**
     * このエンティティに対するルートクエリ
     */
    override val queries: List<Query> by lazy {
        QueryRecord.from(_record.getList("queries", emptyList()), _context, this)
    }
    /**
     * 一意識別キーとなるプロパティのリスト
     */
    override val primaryKeys: List<Property> by lazy {
        (supertype?.primaryKeys ?: emptyList()) + properties.filter{ it.primaryKey }
    }
    /**
     * このエンティティの導出元エンティティ このエンティティが導出エンティティでなければ空集合
     */
    override val inheritedFrom: List<Relationship> by lazy {
        supertype?.inheritedFrom ?: relationships.filter{ it.inherited }
    }
    /**
     * The owned_by of this entity.
     */
    override val ownedBy: Relationship? by lazy {
        supertype?.ownedBy ?: relationships.find{ it.reverse?.aggregate ?: false }
    }
    /**
     * The relationship expresses the ownership of this entity
     */
    override val ownership: Relationship? by lazy {
        ownedBy?.reverse
    }
    /**
     * The entity this entity owns
     */
    override val owner: Entity? by lazy {
        ownership?.entity
    }
    /**
     * The aggregation tree this entity is owned
     */
    override val ownershipHierarchy: List<Relationship> by lazy {
        supertype?.ownershipHierarchy ?:
            if (ownership == null) emptyList()
            else ownership!!.entity.ownershipHierarchy + ownership!!
    }
    /**
     * The root_owner of this entity.
     */
    override val rootOwner: Entity? by lazy {
        supertype?.rootOwner ?: owner?.rootOwner ?: owner
    }
    /**
     * このエンティティが参照するエンティティの一覧(自身は除く)
     */
    override val relatingEntities: List<Entity> by lazy {
        relationships
            .map{ it.referenceEntity }
            .filter{ it.fqn != this.fqn }
            .distinctBy{ it.fqn }
    }
    /**
     * このエンティティが参照するトップレベルエンティティの一覧(自身は除く)
     */
    override val relatingTopLevelEntities: List<Entity> by lazy {
        relatingEntities.filter{ !it.inherited }
    }
    /**
     * このエンティティが参照する外部パッケージのエンティティ
     */
    override val relatingExternalEntities: List<Entity> by lazy {
        relatingEntities.filter{ it.namespace != namespace }
    }
    /**
     * このエンティティが管理する集約
     */
    override val aggregates: List<Relationship> by lazy {
        relationships.filter{ it.aggregate }
    }
    /**
     * aggregates owned by this entity or its ancestors
     */
    override val allAggregates: List<Relationship> by lazy {
        allRelationships.filter{ it.aggregate }
    }
    /**
     * このエンティティに集約されているエンティティの一覧 (再帰的に集約されているものを含む)
     */
    override val aggregatedEntities: List<Entity> by lazy {
        (listOf(this) + aggregates.flatMap {
            it.referenceEntity.aggregatedEntities
        }).distinctBy{ it.fqn }
    }
    /**
     * このエンティティが直接値を保持するプロパティ
     */
    override val storedProperties: List<Property> by lazy {
        properties.filter{ it.snippet == null }
    }
    /**
     * このエンティティが直接関連値を保持している関連
     */
    override val storedRelationships: List<Relationship> by lazy {
        relationships.filter{ it.aggregate || it.mappings.isNotEmpty() }
    }
    /**
     * Returns wether this instance is a entity or not.
     */
    override val isaEntity: Boolean = true

    companion object {
        /**
         * Creates record list from list of map.
         */
        fun from(_context: Context): AllEntities {
            return _context.get("entities") as AllEntities
        }
        fun from(records: RecordList, _context: Context) = records.map { from(it, _context) }

        fun from(record: Record, _context: Context) =
            EntityRecord(record, _context)
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EntityRecord) return false
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
        return "EntityRecord(" +
            "name=$name, " +
            "namespace=$namespace" +
        ")"
    }
}
