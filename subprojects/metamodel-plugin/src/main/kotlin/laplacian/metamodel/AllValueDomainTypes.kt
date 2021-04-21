package laplacian.metamodel
import com.github.jknack.handlebars.Context

import laplacian.util.*

/**
 * All value_domain_types.
 */
class AllValueDomainTypes(
    list: List<ValueDomainType>,
    val context: Context
) : List<ValueDomainType> by list {
    val inNamespace: List<ValueDomainType>
        get() = filter {
            it.namespace.startsWith(context.get("project.namespace") as String)
        }
}
