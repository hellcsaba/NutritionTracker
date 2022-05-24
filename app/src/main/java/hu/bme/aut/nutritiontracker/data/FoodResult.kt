package hu.bme.aut.nutritiontracker.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoodResult(
    val text: String?,
    val parsed: List<Parsed>?,
    val hints: List<Hint>?,
    val _links: Link?
)

@JsonClass(generateAdapter = true)
data class Parsed(
    val food: Food?,
    val quantity: Double?,
    val measure: Measure?
)

@JsonClass(generateAdapter = true)
data class Food(
    val foodId: String?,
    val label: String?,
    val nutrients: Nutrients?,
    val brand: String?,
    val category: String?,
    val categoryLabel: String?,
    val foodContentsLabel: String?,
    val image: String?,
    val servingSizes: List<ServingSize>?,
    val servingsPerContainer: Double?
)

@JsonClass(generateAdapter = true)
data class Measure(
    val uri: String?,
    val label: String?,
    val weight: Double?,
    val qualified: List<QualifierWithRelatedWeight>?
)

@JsonClass(generateAdapter = true)
data class Nutrients(
    val ENERC_KCAL: Double?,
    val PROCNT: Double?,
    val FAT: Double?,
    val CHOCDF: Double?,
    val FIBTG: Double?
)

@JsonClass(generateAdapter = true)
data class ServingSize(
    val uri: String?,
    val label: String?,
    val quantity: Double?
)

@JsonClass(generateAdapter = true)
data class QualifierWithRelatedWeight(
    val qualifiers: List<Qualifier>?,
    val weight: Double?
)

@JsonClass(generateAdapter = true)
data class Qualifier(
    val uri: String?,
    val label: String?
)

@JsonClass(generateAdapter = true)
data class Hint(
    val food: Food,
    val measures: List<Measure>,

    )

@JsonClass(generateAdapter = true)
data class Link(
    val next: Next?
)

@JsonClass(generateAdapter = true)
data class Next(
    val title: String?,
    val href: String?
)