

# **Relationship**
**namespace:** laplacian.metamodel

An entity describing a relationship.



---

## Properties

### name: `String`
The name of this relationship.
- **Attributes:** *PK*

### identifier: `String`
The identifier of this relationship.
- **Default Value:**
  ```kotlin
  name.lowerUnderscorize()
  ```

### cardinality: `String`
The cardinality of this relationship.

### reference_entity_name: `String`
The reference_entity_name of this relationship.

### reference_entity_namespace: `String`
The reference_entity_namespace of this relationship.
- **Default Value:**
  ```kotlin
  entity.namespace
  ```

### aggregate: `Boolean`
Defines this relationship is aggregate or not.
- **Default Value:**
  ```kotlin
  false
  ```

### inherited: `Boolean`
Defines this relationship is inherited or not.
  *Warning: This relationship is deprecated and may be deleted in the future release.*

- **Default Value:**
  ```kotlin
  reverseOf != null
  ```

### reverse_of: `String`
The reverse_of of this relationship.

### description: `String`
The description of this relationship.
- **Default Value:**
  ```kotlin
  "The $name of this ${entity.name}."
  ```

### snippet: `String`
The snippet of this relationship.

### class_name: `String`
The class_name of this relationship.
- **Code:**
  ```kotlin
  if (multiple) "List<${referenceEntity.className}>" else (referenceEntity.className + if (nullable) "?" else "")
  ```

### multiple: `Boolean`
Defines this relationship is multiple or not.
- **Code:**
  ```kotlin
  cardinality.contains("""(\*|N|\.\.[2-9][0-9]+)""".toRegex())
  ```

### allows_empty: `Boolean`
Defines this relationship is allows_empty or not.
- **Code:**
  ```kotlin
  cardinality == "N" || cardinality == "*" || cardinality.contains("""(0\.\.)""".toRegex())
  ```

### nullable: `Boolean`
Defines this relationship is nullable or not.
- **Code:**
  ```kotlin
  !multiple && allowsEmpty
  ```

### property_name: `String`
The property_name of this relationship.
- **Code:**
  ```kotlin
  identifier.lowerCamelize()
  ```

### bidirectional: `Boolean`
Defines this relationship is bidirectional or not.
- **Code:**
  ```kotlin
  aggregate && referenceEntity.relationships.any {
      it.inherited && (it.referenceEntity.fqn == this.entity.fqn)
  }
  ```

### recursive: `Boolean`
Defines this relationship is recursive or not.
- **Code:**
  ```kotlin
  aggregate && (referenceEntity.fqn == entity.fqn)
  ```

### deprecated: `Boolean`
Defines this relationship is deprecated or not.
- **Default Value:**
  ```kotlin
  false
  ```

### examples: `List<String>`
The examples of this relationship.
- **Default Value:**
  ```kotlin
  emptyList<String>()
  ```

## Relationships

### entity: `Entity`
The entity of this relationship.
- **Cardinality:** `1`

### mappings: `List<PropertyMapping>`
The mappings of this relationship.
- **Cardinality:** `*`

### reference_entity: `Entity`
The reference_entity of this relationship.
- **Cardinality:** `1`

### reverse: `Relationship?`
The reverse of this relationship.
- **Cardinality:** `0..1`
- **Code:**
  ```kotlin
  referenceEntity.relationships.find{ it.name == reverseOf }
  ```
