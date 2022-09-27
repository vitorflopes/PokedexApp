package com.pbvitorlucas.myapplication.model.apiModel

data class Ability(
    val id: Int,
    val name: String,
    val is_main_series: Boolean,
    val generation: NamedApiResource,
    val names: List<Name>,
    val effect_entries: List<VerboseEffect>,
    val effect_changes: List<AbilityEffectChange>,
    val flavor_text_entries: List<AbilityFlavorText>,
    val pokemon: List<AbilityPokemon>
)

data class AbilityEffectChange(
    val effect_entries: List<Effect>,
    val version_group: NamedApiResource
)

data class AbilityFlavorText(
    val flavor_text: String,
    val language: NamedApiResource,
    val version_group: NamedApiResource
)

data class AbilityPokemon(
    val is_hidden: Boolean,
    val slot: Int,
    val pokemon: NamedApiResource
)

data class Characteristic(
    val id: Int,
    val gene_modulo: Int,
    val possible_values: List<Int>,
    val descriptions: List<Description>
)

data class EggGroup(
    val id: Int,
    val name: String,
    val names: List<Name>,
    val pokemon_species: List<NamedApiResource>
)

data class Gender(
    val id: Int,
    val name: String,
    val pokemon_species_setails: List<PokemonSpeciesGender>,
    val required_for_evolution: List<NamedApiResource>
)

data class PokemonSpeciesGender(
    val rate: Int,
    val pokemon_species: NamedApiResource
)

data class GrowthRate(
    val id: Int,
    val name: String,
    val formula: String,
    val descriptions: List<Description>,
    val levels: List<GrowthRateExperienceLevel>,
    val pokemon_species: List<NamedApiResource>
)

data class GrowthRateExperienceLevel(
    val level: Int,
    val experience: Int
)

data class Nature(
    val id: Int,
    val name: String,
    val decreased_stat: NamedApiResource?,
    val increased_stat: NamedApiResource?,
    val hates_flavor: NamedApiResource?,
    val likes_flavor: NamedApiResource?,
    val pokeathlon_stat_changes: List<NatureStatChange>,
    val move_battle_style_preferences: List<MoveBattleStylePreference>,
    val names: List<Name>
)

data class PokemonNature(
    val results: List<Nature>
)

data class NatureStatChange(
    val max_change: Int,
    val pokeathlon_stat: NamedApiResource
)

data class MoveBattleStylePreference(
    val low_hp_preference: Int,
    val high_hp_preference: Int,
    val move_battle_style: NamedApiResource
)

data class PokeathlonStat(
    val id: Int,
    val name: String,
    val names: List<Name>,
    val affecting_natures: NaturePokeathlonStatAffectSets
)

data class NaturePokeathlonStatAffectSets(
    val increase: List<NaturePokeathlonStatAffect>,
    val decrease: List<NaturePokeathlonStatAffect>
)

data class NaturePokeathlonStatAffect(
    val max_change: Int,
    val nature: NamedApiResource
)

data class Pokemon(
    val id: Int,
    val name: String,
    val base_experience: Int,
    val height: Int,
    val is_default: Boolean,
    val order: Int,
    val weight: Int,
    val species: NamedApiResource,
    val abilities: List<PokemonAbility>,
    val forms: List<NamedApiResource>,
    val game_indices: List<VersionGameIndex>,
    val held_items: List<PokemonHeldItem>,
    val moves: List<PokemonMove>,
    val stats: List<PokemonStat>,
    val types: List<PokemonType>,
    val sprites: PokemonSprites,

    var campeaoId: String? = null
) {
}

data class PokemonSprites(
    val front_default: String?,
    val front_shiny: String?,
    val front_female: String?,
    val front_shiny_female: String?,
    val back_default: String?,
    val back_shiny: String?,
    val back_female: String?,
    val back_shiny_female: String?

)

data class PokemonAbility(
    val is_hidden: Boolean,
    val slot: Int,
    val ability: NamedApiResource
)

data class PokemonHeldItem(
    val item: NamedApiResource,
    val version_details: List<PokemonHeldItemVersion>
)

data class PokemonHeldItemVersion(
    val version: NamedApiResource,
    val rarity: Int
)

data class PokemonMove(
    val move: NamedApiResource,
    val version_group_details: List<PokemonMoveVersion>
)

data class PokemonMoveVersion(
    val move_learn_method: NamedApiResource,
    val version_group: NamedApiResource,
    val level_learned_at: Int
)

data class PokemonStat(
    val stat: NamedApiResource,
    val effort: Int,
    val base_stat: Int
)

data class PokemonType(
    val slot: Int,
    val type: NamedApiResource
)

data class LocationAreaEncounter(
    val location_area: NamedApiResource,
    val version_details: List<VersionEncounterDetail>
)

data class PokemonColor(
    val id: Int,
    val name: String,
    val names: List<Name>,
    val pokemon_species: List<NamedApiResource>
)

data class PokemonForm(
    val id: Int,
    val name: String,
    val order: Int,
    val form_order: Int,
    val is_default: Boolean,
    val is_battle_only: Boolean,
    val is_mega: Boolean,
    val form_name: String,
    val pokemon: NamedApiResource,
    val version_group: NamedApiResource,
    val sprites: PokemonFormSprites,
    val form_names: List<Name>
)

data class PokemonFormSprites(
    val back_default: String?,
    val back_shiny: String?,
    val front_default: String?,
    val front_shiny: String?
)

data class PokemonHabitat(
    val id: Int,
    val name: String,
    val names: List<Name>,
    val pokemon_species: List<NamedApiResource>
)

data class PokemonShape(
    val id: Int,
    val name: String,
    val awesome_names: List<AwesomeName>,
    val names: List<Name>,
    val pokemon_species: List<NamedApiResource>
)

data class AwesomeName(
    val awesome_name: String,
    val language: NamedApiResource
)

data class PokemonSpecies(
    val id: Int,
    val name: String,
    val order: Int,
    val gender_rate: Int,
    val capture_rate: Int,
    val base_happiness: Int,
    val is_baby: Boolean,
    val is_legendary: Boolean,
    val is_mythical: Boolean,
    val hatch_counter: Int,
    val has_gender_differences: Boolean,
    val forms_switchable: Boolean,
    val growth_rate: NamedApiResource,
    val pokedex_numbers: List<PokemonSpeciesDexEntry>,
    val egg_groups: List<NamedApiResource>,
    val color: NamedApiResource,
    val shape: NamedApiResource,
    val evolves_from_species: NamedApiResource?,
    val evolution_chain: ApiResource,
    val habitat: NamedApiResource?,
    val generation: NamedApiResource,
    val names: List<Name>,
    val pal_park_encounters: List<PalParkEncounterArea>,
    val form_descriptions: List<Description>,
    val genera: List<Genus>,
    val varieties: List<PokemonSpeciesVariety>,
    val flavor_text_entries: List<PokemonSpeciesFlavorText>
)

data class PokemonSpeciesFlavorText(
    val flavor_text: String,
    val language: NamedApiResource,
    val version: NamedApiResource
)

data class Genus(
    val genus: String,
    val language: NamedApiResource
)

data class PokemonSpeciesDexEntry(
    val entry_number: Int,
    val pokedex: NamedApiResource
)

data class PalParkEncounterArea(
    val base_score: Int,
    val rate: Int,
    val area: NamedApiResource
)

data class PokemonSpeciesVariety(
    val is_default: Boolean,
    val pokemon: NamedApiResource
)

data class Stat(
    val id: Int,
    val name: String,
    val game_index: Int,
    val is_battle_only: Boolean,
    val affecting_moves: MoveStatAffectSets,
    val affecting_natures: NatureStatAffectSets,
    val characteristics: List<ApiResource>,
    val move_damage_class: NamedApiResource?,
    val names: List<Name>
)

data class MoveStatAffectSets(
    val increase: List<MoveStatAffect>,
    val decrease: List<MoveStatAffect>
)

data class MoveStatAffect(
    val change: Int,
    val move: NamedApiResource
)

data class NatureStatAffectSets(
    val increase: List<NamedApiResource>,
    val decrease: List<NamedApiResource>
)

data class Type(
    val id: Int,
    val name: String,
    val past_damage_relations: List<TypeRelationsPast>,
    val game_indices: List<GenerationGameIndex>,
    val generation: NamedApiResource,
    val move_damage_class: NamedApiResource?,
    val names: List<Name>,
    val pokemon: List<TypePokemon>,
    val moves: List<NamedApiResource>
)

data class TypePokemon(
    val slot: Int,
    val pokemon: NamedApiResource
)

data class TypeRelations(
    val no_damage_to: List<NamedApiResource>,
    val half_damage_to: List<NamedApiResource>,
    val double_damage_to: List<NamedApiResource>,
    val no_damage_from: List<NamedApiResource>,
    val half_damage_from: List<NamedApiResource>,
    val double_damage_from: List<NamedApiResource>
)

data class TypeRelationsPast(
    val generation: Generation,
    val damage_relations: TypeRelations
)