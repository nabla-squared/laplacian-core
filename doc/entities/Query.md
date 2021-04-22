

# **Query**
**namespace:** laplacian.metamodel

The queries from which all navigation originates.




---

## Properties

### name: `String`
クエリ名称

### identifier: `String`
識別子
- **Default Value:**
  ```kotlin
  name.lowerUnderscorize()
  ```

### type: `String`
結果型
- **Default Value:**
  ```kotlin
  resultEntity?.className?.let { className ->
      if (cardinality.contains("*")) "List<$className>" else className
  }
  ```

### result_entity_name: `String`
クエリ結果エンティティ名

### description: `String`
詳細
- **Default Value:**
  ```kotlin
  name
  ```

### cardinality: `String`
多重度
- **Default Value:**
  ```kotlin
  "*"
  ```

### snippet: `String`
クエリスクリプト

### oneliner: `Boolean`
Defines this query is oneliner or not.
- **Code:**
  ```kotlin
  !snippet.contains("""\breturn\b""".toRegex())
  ```

### deprecated: `Boolean`
Defines this query is deprecated or not.
- **Default Value:**
  ```kotlin
  false
  ```

## Relationships

### entity: `Entity`
エンティティ
- **Cardinality:** `1`

### result_entity: `Entity?`
クエリ結果エンティティ
- **Cardinality:** `0..1`
