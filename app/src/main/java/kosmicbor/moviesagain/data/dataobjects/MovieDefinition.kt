package kosmicbor.moviesagain.data.dataobjects

data class MovieDefinition(
    val backdropPath: String?,
    val budget: Int,
    val genres: List<Genre>,
    val id: Int,
    val originalTitle: String,
    val overview: String?,
    val popularity: Number,
    val posterPath: String?,
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val voteAverage: Number,
    val voteCount: Number
)


/*
    "backdrop_path": "/rr7E0NoGKxvbkb89eR1GwfoYjpA.jpg",
    "belongs_to_collection": null,
    "budget": 63000000,
    "genres": [
        {
            "id": 18,
            "name": "Drama"
        },
        {
            "id": 53,
            "name": "Thriller"
        },
        {
            "id": 35,
            "name": "Comedy"
        }
    ],
    "id": 550,
    "original_title": "Fight Club",
    "overview": "A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.",
    "popularity": 83.466,
    "poster_path": "/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
    "release_date": "1999-10-15",
    "revenue": 100853753,
    "runtime": 139,
    "status": "Released",
    "vote_average": 8.431,
    "vote_count": 24997
*/