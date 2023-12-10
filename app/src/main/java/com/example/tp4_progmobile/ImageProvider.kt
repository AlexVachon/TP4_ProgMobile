package com.example.tp4_progmobile

/**
 * Classe qui gère les catégories des items de Firebase
 */
class ImageProvider {
    companion object{
        private val imageList = listOf<Int>(
            R.drawable.meuble,
            R.drawable.automobile,
            R.drawable.fitness,
            R.drawable.jouet,
            R.drawable.nourriture,
            R.drawable.plein_air,
            R.drawable.video_games
        )
        private val categories = listOf<String>(
            "Meuble",
            "Automobile",
            "Fitness",
            "Jouet",
            "Nourriture",
            "Plein air",
            "Jeux vidéo"
        )

        fun getImageAtIndex(index: Int): Int {
            return imageList[index]
        }
        fun getCategoryAtIndex(index: Int): String {
            return categories[index]
        }
    }
}