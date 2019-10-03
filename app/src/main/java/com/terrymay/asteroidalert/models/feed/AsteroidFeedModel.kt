package com.terrymay.asteroidalert.models.feed

data class AsteroidFeedModel(
    val element_count: Int,
    val links: Links,
    val near_earth_objects: Map<String, List<NearEarthObject>>
)