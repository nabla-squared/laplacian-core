

# **Property**
**namespace:** laplacian.metamodel

An entity describing a property.



---

## Properties

### name: `String`
The name of this property.
- **Attributes:** *PK*

### identifier: `String`
The identifier of this property.
- **Default Value:**
  ```kotlin
  name.lowerUnderscorize()
  ```

### primary_key: `Boolean`
Defines this property is primary_key or not.
- **Default Value:**
  ```kotlin
  false
  ```

### subtype_key: `Boolean`
Defines this property is subtype_key or not.
- **Default Value:**
  ```kotlin
  false
  ```

### type: `String`
The type of this property.

### domain_type_name: `String`
The name of predefined value domain type for this property.

### size: `Int`
The maximum allowed size of the content of this property.
- **Default Value:**
  ```kotlin
  when(type) {
      "string" -> 200
      else -> 0
  }
  ```

### optional: `Boolean`
Defines this property is optional or not.
- **Default Value:**
  ```kotlin
  false
  ```

### description: `String`
The description of this property.
- **Default Value:**
  ```kotlin
  if (type == "boolean") "Defines this ${entity.name} is $name or not." else "The $name of this ${entity.name}."
  ```

### default_value: `String`
The default value of this property, which is used when the actual value is null


### example_values: `List<String>`
Kotlin expressions which represent some typical values of this property.

- **Default Value:**
  ```kotlin
  emptyList<String>()
  ```

### example_value: `String`
A kotlin expression which represents a typical value of this property.

- **Code:**
  ```kotlin
  when {
    !exampleValues.isEmpty() -> exampleValues.first()
    (defaultValue != null) -> defaultValue.toString()
    multiple -> "emptyList<${className}>()"
    (type == "string") -> "\"hogehoge\""
    (type == "number") -> "42"
    else -> "null"
  }
  ```

### table_column_name: `String`
The table_column_name of this property.
- **Default Value:**
  ```kotlin
  identifier.lowerUnderscorize()
  ```

### snippet: `String`
The snippet of this property.

### multiple: `Boolean`
Defines this property is multiple or not.
- **Default Value:**
  ```kotlin
  false
  ```

### property_name: `String`
The property_name of this property.
- **Code:**
  ```kotlin
  identifier.lowerCamelize()
  ```

### class_name: `String`
The class_name of this property.
- **Code:**
  ```kotlin
  when(type) {
      "number" -> "Int"
      "date", "datetime", "time" -> "String"
      "object", "any_entity" -> "Any"
      else -> type.upperCamelize()
  }
  .let {
      if (multiple) "List<$it>" else it
  }
  ```

### nullable: `Boolean`
Whether this property permits a null value

- **Code:**
  ```kotlin
  optional && !multiple && (defaultValue == null)
  ```

### overwrites: `Boolean`
Whether this preoperty overwrite a property which is defined in supertype.

- **Code:**
  ```kotlin
  entity.ancestors.any{ s -> s.properties.any{ p -> p.name == name }}
  ```

### deprecated: `Boolean`
Defines this property is deprecated or not.
- **Default Value:**
  ```kotlin
  false
  ```

## Relationships

### entity: `Entity`
The entity of this property.
- **Cardinality:** `1`

### domain: `ValueDomain?`
The domain of this property.
- **Cardinality:** `0..1`

### domain_type: `ValueDomainType?`
The domain_type of this property.
- **Cardinality:** `0..1`
