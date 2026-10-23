package com.royals.class11physics

data class Chapter(
    val number: Int,
    val title: String,
    val fileName: String,
    val unit: String
)

val chapters = listOf(
    Chapter(1, "Physical World", "chapter_01.html", "Unit 1: Physical World & Measurement"),
    Chapter(2, "Units and Measurements", "chapter_02.html", "Unit 1: Physical World & Measurement"),
    Chapter(3, "Motion in a Straight Line", "chapter_03.html", "Unit 2: Kinematics"),
    Chapter(4, "Motion in a Plane", "chapter_04.html", "Unit 2: Kinematics"),
    Chapter(5, "Laws of Motion", "chapter_05.html", "Unit 3: Laws of Motion"),
    Chapter(6, "Work, Energy and Power", "chapter_06.html", "Unit 4: Work, Energy and Power"),
    Chapter(7, "System of Particles and Rotational Motion", "chapter_07.html", "Unit 5: Rotational Motion"),
    Chapter(8, "Gravitation", "chapter_08.html", "Unit 6: Gravitation"),
    Chapter(9, "Mechanical Properties of Solids", "chapter_09.html", "Unit 7: Properties of Bulk Matter"),
    Chapter(10, "Mechanical Properties of Fluids", "chapter_10.html", "Unit 7: Properties of Bulk Matter"),
    Chapter(11, "Thermal Properties of Matter", "chapter_11.html", "Unit 7: Properties of Bulk Matter"),
    Chapter(12, "Thermodynamics", "chapter_12.html", "Unit 8: Thermodynamics"),
    Chapter(13, "Kinetic Theory", "chapter_13.html", "Unit 9: Kinetic Theory of Gases"),
    Chapter(14, "Oscillations", "chapter_14.html", "Unit 10: Oscillations and Waves"),
    Chapter(15, "Waves", "chapter_15.html", "Unit 10: Oscillations and Waves")
)
