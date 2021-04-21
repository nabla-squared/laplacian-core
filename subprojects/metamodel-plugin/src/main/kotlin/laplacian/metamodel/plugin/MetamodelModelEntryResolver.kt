package laplacian.metamodel.plugin
import org.pf4j.Extension
import laplacian.generate.ModelEntryResolver
import laplacian.generate.ExecutionContext
import laplacian.metamodel.AllEntities
import laplacian.metamodel.EntityRecord
import laplacian.metamodel.AllValueDomainTypes
import laplacian.metamodel.ValueDomainTypeRecord

import laplacian.generate.util.*

@Extension
class MetamodelModelEntryResolver: ModelEntryResolver {

    override fun resolves(key: String, model: Map<String, Any?>): Boolean {
        return arrayOf(
            "entities",
            "value_domain_types"
        ).any { it == key }
    }

    override fun resolve(key: String, model: Map<String, Any?>, context: ExecutionContext): Any? {
        return when (key) {
            "entities" -> AllEntities(
                model.getList<Record>("entities", emptyList())
                     .mergeWithKeys("name", "namespace")
                     .let{ EntityRecord.from(it, context.currentModel) },
                context.currentModel
            )
            "value_domain_types" -> AllValueDomainTypes(
                model.getList<Record>("value_domain_types", emptyList())
                     .mergeWithKeys("name", "namespace")
                     .let{ ValueDomainTypeRecord.from(it, context.currentModel) },
                context.currentModel
            )
            else -> throw IllegalArgumentException("Unknown key: $key")
        }
    }
}
