package edu.umich.fullgroup.perminit_frontend

import kotlinx.serialization.Serializable

@Serializable
data class PerMinit (val name: String,
                     val personality: String,
                     val examples: String,
                     var profileImage: String) {
}