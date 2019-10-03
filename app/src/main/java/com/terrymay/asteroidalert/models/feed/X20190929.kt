package com.terrymay.asteroidalert.models.feed

data class X20190929(
    val absolute_magnitude_h: Double,
    val close_approach_data: List<CloseApproachData>,
    val estimated_diameter: EstimatedDiameter,
    val id: String,
    val is_potentially_hazardous_asteroid: Boolean,
    val is_sentry_object: Boolean,
    val links: LinksX,
    val name: String,
    val nasa_jpl_url: String,
    val neo_reference_id: String
)