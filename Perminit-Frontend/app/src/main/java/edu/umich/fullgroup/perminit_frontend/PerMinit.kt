package edu.umich.fullgroup.perminit_frontend

data class PerMinit (val name: String,
                     val personality: String,
                     val ability: String,
                     var profileImage: String){
    var reminder: String = ""
}