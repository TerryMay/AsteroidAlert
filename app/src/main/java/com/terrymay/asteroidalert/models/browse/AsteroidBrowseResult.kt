package com.terrymay.asteroidalert.models.browse

data class AsteroidBrowseResult(
    val links: Links,
    val near_earth_objects: List<NearEarthObject>,
    val page: Page
)