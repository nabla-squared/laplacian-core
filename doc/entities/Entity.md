

# **Entity**
**namespace:** laplacian.metamodel

An entity describing a entity.


example1:
```yaml
name: party
namespace: example.party
properties:
- name: party_id
  type: string
  primary_key: true
- name: party_type
  type: string
  subtype_key: true

```

example2:
```yaml
name: person
namespace: example.party
subtype_of: party
properties:
- name: last_name
  type: string
- name: first_name
  type: string
- name: middle_name
  type: string
  optional: true

relationships:
- name: physical_characteristics
  reference_entity_name: person_physical_characteristic
  cardinality: '*'
  aggregate: true

```

example3:
```yaml
name: organization
namespace: example.party
subtype_of: party
properties:
- name: name
  type: string

```

example4:
```yaml
name: person_physical_characteristic
namespace: example.party
properties:
- name: type
  type: string

- name: from_date
  type: string

- name: thru_date
  type: string

- name: value
  type: string

relationships:
- name: person
  reference_entity_name: person
  cardinality: '1'
  reverse_of: physical_characteristics

- name: characteristic_type
  reference_entity_name: person_physical_characteristic_type
  cardinality: '1'
  mappings:
  - from: type
    to: name

```

example5:
```yaml
name: person_physical_characteristic_type
namespace: example.party
properties:
- name: name
  type: string
  primary_key: true

- name: description
  type: string

```


---

## Properties

### name: `String`
The name of this entity.
- **Attributes:** *PK*

### namespace: `String`
The namespace of this entity.
- **Attributes:** *PK*

### identifier: `String`
The identifier of this entity.
- **Default Value:**
  ```kotlin
  name.lowerUnderscorize()
  ```

### singly_rooted: `Boolean`
If this property is true, there is the "root" instance, which is accessible globally.

- **Default Value:**
  ```kotlin
  false
  ```

### description: `String`
The description of this entity.
- **Default Value:**
  ```kotlin
  "An entity describing a ${name}."
  ```

### value_object: `Boolean`
Defines this entity is value_object or not.
- **Default Value:**
  ```kotlin
  false
  ```

### class_name: `String`
The class_name of this entity.
- **Default Value:**
  ```kotlin
  name.upperCamelize()
  ```

### table_name: `String`
The table_name of this entity.
- **Default Value:**
  ```kotlin
  "t_${name.lowerUnderscorize()}"
  ```

### supertype_name: `String`
The supertype_name of this entity.

### supertype_namespace: `String`
The supertype_namespace of this entity.
- **Default Value:**
  ```kotlin
  namespace
  ```

### subtype_key_value: `String`
The value of subtype key that represents this type of entity,
which is used when implementing polymorphism. The name of entity is used by default.

- **Default Value:**
  ```kotlin
  name
  ```

### inherited: `Boolean`
Deprecated: prefer to use the 'reverse_of' property

  *Warning: This relationship is deprecated and may be deleted in the future release.*

- **Code:**
  ```kotlin
  supertype?.inherited ?: relationships.any{ it.inherited || it.reverse != null }
  ```

### top_level: `Boolean`
Defines this entity is top_level or not.
- **Code:**
  ```kotlin
  (owner == null || owner == this) && (supertype == null) && !valueObject
  ```

### supports_namespace: `Boolean`
Defines this entity is supports_namespace or not.
- **Code:**
  ```kotlin
  properties.any { p ->
      p.name == "namespace" && p.type == "string"
  }
  ```

### fqn: `String`
The fqn of this entity.
- **Code:**
  ```kotlin
  "$namespace.$className"
  ```

### primary_key_names: `List<String>`
The primary_key_names of this entity.
  *Warning: This relationship is deprecated and may be deleted in the future release.*

- **Code:**
  ```kotlin
  inheritedFrom.flatMap { inheritance ->
      inheritance.referenceEntity.primaryKeys.map { pk ->
          "${inheritance.identifier.lowerUnderscorize()}_${pk.propertyName.lowerUnderscorize()}"
      }
  } + primaryKeys.map { it.propertyName.lowerUnderscorize() }
  ```

### deprecated: `Boolean`
Defines this entity is deprecated or not.
- **Default Value:**
  ```kotlin
  false
  ```

### examples: `List<String>`
examples which explain actual usage of this entity
- **Default Value:**
  ```kotlin
  emptyList<String>()
  ```

## Relationships

### properties: `List<Property>`
The properties of this entity (excluding supertypes')
- **Cardinality:** `*`

### all_properties: `List<Property>`
The properties of this entity
- **Cardinality:** `1..*`
- **Code:**
  ```kotlin
  (supertype?.allProperties ?: emptyList()) + properties
  ```

### relationships: `List<Relationship>`
The relationships with other entities (excluding supertypes')
- **Cardinality:** `*`

### all_relationships: `List<Relationship>`
The relationships including supertype's ones.
- **Cardinality:** `*`
- **Code:**
  ```kotlin
  (supertype?.allRelationships ?: emptyList()) + relationships
  ```

### supertype: `Entity?`
The entity which this entity is subtype of
- **Cardinality:** `0..1`

### root: `Entity`
The root entity of the inheritance tree including this entity.

- **Cardinality:** `1`
- **Code:**
  ```kotlin
  if (supertype != null) ancestors.last() else this
  ```

### ancestors: `List<Entity>`
The entities which are supertype of this entity (recursive).

- **Cardinality:** `*`
- **Code:**
  ```kotlin
  mutableListOf<Entity>().also {
      var ancestor = supertype
      while (ancestor != null) {
          it.add(ancestor)
          ancestor = ancestor.supertype
      }
  }
  ```

### subtypes: `List<Entity>`
The subtype entities of this entity
- **Cardinality:** `*`

### descendants: `List<Entity>`
All the subtypes of this entity
- **Cardinality:** `*`
- **Code:**
  ```kotlin
  subtypes + subtypes.flatMap{ it.descendants }
  ```

### subtype_key: `Property?`
The property which is used to identify the type of a entity.
- **Cardinality:** `0..1`
- **Code:**
  ```kotlin
  properties.find{ it.subtypeKey }
  ```

### queries: `List<Query>`
このエンティティに対するルートクエリ
- **Cardinality:** `*`

### primary_keys: `List<Property>`
一意識別キーとなるプロパティのリスト
- **Cardinality:** `1..*`
- **Code:**
  ```kotlin
  (supertype?.primaryKeys ?: emptyList()) + properties.filter{ it.primaryKey }
  ```

### inherited_from: `List<Relationship>`
このエンティティの導出元エンティティ このエンティティが導出エンティティでなければ空集合

  *Warning: This relationship is deprecated and may be deleted in the future release.*

- **Cardinality:** `*`
- **Code:**
  ```kotlin
  supertype?.inheritedFrom ?: relationships.filter{ it.inherited }
  ```

### owned_by: `Relationship?`
The owned_by of this entity.
- **Cardinality:** `0..1`
- **Code:**
  ```kotlin
  supertype?.ownedBy ?: relationships.find{ it.reverse?.aggregate ?: false }
  ```

### ownership: `Relationship?`
The relationship expresses the ownership of this entity

- **Cardinality:** `0..1`
- **Code:**
  ```kotlin
  ownedBy?.reverse
  ```

### owner: `Entity?`
The entity this entity owns
- **Cardinality:** `0..1`
- **Code:**
  ```kotlin
  ownership?.entity
  ```

### ownership_hierarchy: `List<Relationship>`
The aggregation tree this entity is owned

- **Cardinality:** `*`
- **Code:**
  ```kotlin
  supertype?.ownershipHierarchy ?:
      if (ownership == null) emptyList()
      else ownership!!.entity.ownershipHierarchy + ownership!!
  ```

### root_owner: `Entity?`
The root_owner of this entity.
- **Cardinality:** `0..1`
- **Code:**
  ```kotlin
  supertype?.rootOwner ?: owner?.rootOwner ?: owner
  ```

### relating_entities: `List<Entity>`
このエンティティが参照するエンティティの一覧(自身は除く)

- **Cardinality:** `*`
- **Code:**
  ```kotlin
  relationships
      .map{ it.referenceEntity }
      .filter{ it.fqn != this.fqn }
      .distinctBy{ it.fqn }
  ```

### relating_top_level_entities: `List<Entity>`
このエンティティが参照するトップレベルエンティティの一覧(自身は除く)
- **Cardinality:** `*`
- **Code:**
  ```kotlin
  relatingEntities.filter{ !it.inherited }
  ```

### relating_external_entities: `List<Entity>`
このエンティティが参照する外部パッケージのエンティティ
- **Cardinality:** `*`
- **Code:**
  ```kotlin
  relatingEntities.filter{ it.namespace != namespace }
  ```

### aggregates: `List<Relationship>`
このエンティティが管理する集約
- **Cardinality:** `*`
- **Code:**
  ```kotlin
  relationships.filter{ it.aggregate }
  ```

### all_aggregates: `List<Relationship>`
aggregates owned by this entity or its ancestors
- **Cardinality:** `*`
- **Code:**
  ```kotlin
  allRelationships.filter{ it.aggregate }
  ```

### aggregated_entities: `List<Entity>`
このエンティティに集約されているエンティティの一覧 (再帰的に集約されているものを含む)

- **Cardinality:** `*`
- **Code:**
  ```kotlin
  (listOf(this) + aggregates.flatMap {
      it.referenceEntity.aggregatedEntities
  }).distinctBy{ it.fqn }
  ```

### stored_properties: `List<Property>`
このエンティティが直接値を保持するプロパティ
- **Cardinality:** `*`
- **Code:**
  ```kotlin
  properties.filter{ it.snippet == null }
  ```

### stored_relationships: `List<Relationship>`
このエンティティが直接関連値を保持している関連
- **Cardinality:** `*`
- **Code:**
  ```kotlin
  relationships.filter{ it.aggregate || it.mappings.isNotEmpty() }
  ```
