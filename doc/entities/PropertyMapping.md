

# **PropertyMapping**
**namespace:** laplacian.metamodel

An entity describing a property_mapping.



---

## Properties

### from: `String`
The from of this property_mapping.

### to: `String`
The to of this property_mapping.

### null_value: `String`
The null_value of this property_mapping.

## Relationships

### relationship: `Relationship`
The relationship of this property_mapping.
- **Cardinality:** `1`

### property: `Property`
The property of this property_mapping.
- **Cardinality:** `1`
- **Code:**
  ```kotlin
  relationship.entity.properties.find{ it.name == from }!!
  ```

### reference_property: `Property`
The reference_property of this property_mapping.
- **Cardinality:** `1`
- **Code:**
  ```kotlin
  relationship.referenceEntity.properties.find{ it.name == to }!!
  ```
