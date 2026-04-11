package com.arameu.data

data class LetterMapping(
    val hebrew: String,
    val syriac: String,
    val name: String,
)

val ALPHABET_MAPPINGS: List<LetterMapping> = listOf(
    LetterMapping("\u05D0", "\u0710", "Aleph"),
    LetterMapping("\u05D1", "\u0712", "Bet"),
    LetterMapping("\u05D2", "\u0713", "Gimel"),
    LetterMapping("\u05D3", "\u0715", "Dalet"),
    LetterMapping("\u05D4", "\u0717", "He"),
    LetterMapping("\u05D5", "\u0718", "Waw"),
    LetterMapping("\u05D6", "\u0719", "Zayin"),
    LetterMapping("\u05D7", "\u071A", "Het"),
    LetterMapping("\u05D8", "\u071B", "Tet"),
    LetterMapping("\u05D9", "\u071D", "Yod"),
    LetterMapping("\u05DB", "\u071F", "Kaf"),
    LetterMapping("\u05DC", "\u0720", "Lamed"),
    LetterMapping("\u05DE", "\u0721", "Mem"),
    LetterMapping("\u05E0", "\u0722", "Nun"),
    LetterMapping("\u05E1", "\u0723", "Samekh"),
    LetterMapping("\u05E2", "\u0725", "Ayin"),
    LetterMapping("\u05E4", "\u0726", "Pe"),
    LetterMapping("\u05E6", "\u0728", "Tsade"),
    LetterMapping("\u05E7", "\u0729", "Qof"),
    LetterMapping("\u05E8", "\u072A", "Resh"),
    LetterMapping("\u05E9", "\u072B", "Shin"),
    LetterMapping("\u05EA", "\u072C", "Taw"),
)
