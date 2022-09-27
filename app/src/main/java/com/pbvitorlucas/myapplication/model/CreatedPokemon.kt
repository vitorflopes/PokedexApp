package com.pbvitorlucas.myapplication.model

class CreatedPokemon(
    val spriteFront: String? = null,
    val spriteBack: String? = null,
    val nickname: String? = null,
    val species: String? = null,
    val color: String? = null,
    val lvl: String? = null,
    val gender: Boolean? = null,
    val ability: String? = null,
    val item: String? = null,
    val spriteItem: String? = null,
    val iv: List<String>? = null,
    val ev: List<String>? = null,
    val nature: String? = null,
    val moves: List<String>? = null,
    var id: Int? = null,
    val idUsuario: String? = null,
    val idFirebase: String? = null
) {

    override fun toString(): String {
        val name: String
        val ivs: String
        val evs: String

        if(nickname.isNullOrBlank()){
            name = "$species"
        }else{
            name = "$nickname ($species)"
        }

        if(ev.isNullOrEmpty()){
            evs = ""
        }else{
            evs = "EVs: ${ev[0]} HP / ${ev[1]} Atk / ${ev[2]} Def /" +
                    " ${ev[3]} SpA / ${ev[4]} SpD / ${ev[5]} Spe"
        }

        if(iv.isNullOrEmpty()){
            ivs = ""
        }else{
            ivs = "IVs: ${iv[0]} HP / ${iv[1]} Atk / ${iv[2]} Def /" +
                    " ${iv[3]} SpA / ${iv[4]} SpD / ${iv[5]} Spe"
        }

        val pkmn = "$name @ $item\n" +
                "Ability: $ability\n" +
                "$evs\n" +
                "$ivs\n" +
                "$nature Nature\n" +
                "- ${moves!![0]}\n" +
                "- ${moves[1]}\n" +
                "- ${moves[2]}\n" +
                "- ${moves[3]}"

        return pkmn
    }
}