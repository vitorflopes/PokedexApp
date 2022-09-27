package com.pbvitorlucas.myapplication.model.apiModel

data class Machine(
    val id: Int,
    val item: NamedApiResource,
    val move: NamedApiResource,
    val versionGroup: NamedApiResource
)