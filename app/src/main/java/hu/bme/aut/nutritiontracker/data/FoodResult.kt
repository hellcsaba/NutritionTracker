package hu.bme.aut.nutritiontracker.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoodResult(
    val text: String? = null,
    val parsed: List<Parsed>?  = null,
    val hints: List<Hint>?  = null,
    val _links: Link?  = null
)

@JsonClass(generateAdapter = true)
data class Parsed(
    val food: Food?  = null,
    val quantity: Double?  = null,
    val measure: Measure?  = null
)

@JsonClass(generateAdapter = true)
data class Food(
    val foodId: String? = null,
    val label: String? = null,
    val nutrients: Nutrients? = null,
    val brand: String? = null,
    val category: String? = null,
    val categoryLabel: String? = null,
    val foodContentsLabel: String? = null,
    val image: String? = null,
    val servingSizes: List<ServingSize>? = null,
    val servingsPerContainer: Double? = null
)

@JsonClass(generateAdapter = true)
data class Measure(
    val uri: String? = null,
    val label: String? = null,
    val weight: Double? = null,
    val qualified: List<QualifierWithRelatedWeight>?  = null
)

@JsonClass(generateAdapter = true)
data class Nutrients(
    val ENERC_KCAL: Double? = null,
    val PROCNT: Double? = null,
    val FAT: Double? = null,
    val CHOCDF: Double? = null,
    val FIBTG: Double? = null
)

@JsonClass(generateAdapter = true)
data class ServingSize(
    val uri: String? = null,
    val label: String? = null,
    val quantity: Double? = null
)

@JsonClass(generateAdapter = true)
data class QualifierWithRelatedWeight(
    val qualifiers: List<Qualifier>? = null,
    val weight: Double? = null
)

@JsonClass(generateAdapter = true)
data class Qualifier(
    val uri: String? = null,
    val label: String? = null
)

@JsonClass(generateAdapter = true)
data class Hint(
    val food: Food? = null,
    val measures: List<Measure>? = null,

    )

@JsonClass(generateAdapter = true)
data class Link(
    val next: Next? = null
)

@JsonClass(generateAdapter = true)
data class Next(
    val title: String? = null,
    val href: String? = null
)