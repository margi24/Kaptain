package com.example.kaptain.data


val reviewList = listOf(
    Review(
        1,
        "Mr. Beanaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
        "Great Locationaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
        "My bear and I had a great time thereaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
        3.0,
        "2020/10/20",
        46067
    ),
    Review(
        2,
        "John Doe",
        "Great Location",
        "I was found here",
        5.0,
        "2020/10/20",
        12975

    ),
    Review(
        3,
        "Mia Bia",
        "Great Location",
        "Nem tudok angolul",
        1.0,
        "2020/10/20",
        46085
    ),
    Review(
        4,
        "Mr. Bean",
        "Worst place ever",
        "En sem",
        1.0,
        "2020/10/20",
        46085
    ),
    Review(
        5,
        "Robin Hood",
        "Ok",
        "Couldn't find the bad guys ",
        3.0,
        "2020/10/20",
        60928
    ),
    Review(
        6,
        "Mr. Bean",
        "Great Location",
        "My bear and I had a great time there",
        4.0,
        "2020/10/20",
        60928
    )
)

val poiList = listOf(
    PointOfInterest(
        46067,
        MapLocation(
            37.8180564724432,
            -122.52704143524173
        ),
        "Point Bonita",
        "Anchorage",
        ReviewSummary(3.0, 1)
    ),
    PointOfInterest(
        12975,
        MapLocation(
            37.8770892291283,
            -122.503309249878
        ),
        "Richardson Bay Marina",
        "Marina",
        ReviewSummary(5.0, 1)
    ),
    PointOfInterest(
        46085,
        MapLocation(
            37.82878469060811,
            -122.47633210712522
        ),
        "Needles",
        "Anchorage",
        ReviewSummary(1.0, 2)
    ),
    PointOfInterest(
        19637,
        MapLocation(37.82077, -122.4786),
        "Golden Gate Bridge",
        "Bridge",
        ReviewSummary(0.0, 0)
    ),
    PointOfInterest(
        60928,
        MapLocation(
            37.8325155338083,
            -122.47500389814363
        ),
        "Horseshoe Cove",
        "Anchorage",
        ReviewSummary(3.5, 2)
    ),
    PointOfInterest(
        39252,
        MapLocation(37.833886767314, -122.475371360779),
        "Presidio Yacht Club",
        "Marina",
        ReviewSummary(0.0, 0)
    ),
    PointOfInterest(
        25644,
        MapLocation(
            37.8673327691044,
            -122.435932159424
        ),
        "Ayala Cove",
        "Anchorage",
        ReviewSummary(0.0, 0)
    ),
    PointOfInterest(
        61865,
        MapLocation(
            37.850002964208095,
            -122.41632213957898
        ),
        "Tide Rips",
        "Hazard",
        ReviewSummary(0.0, 0)
    ),
    PointOfInterest(
        46713,
        MapLocation(
            37.827799573006274,
            -122.42648773017541
        ),
        "Dangerous Rock",
        "Hazard",
        ReviewSummary(0.0, 0)
    ),
    PointOfInterest(
        57109,
        MapLocation(
            37.87572310328571,
            -122.50570595169079
        ),
        "Woodrum Marine Boat Repair/Carpentry",
        "Business",
        ReviewSummary(0.0, 0)
    )
)
