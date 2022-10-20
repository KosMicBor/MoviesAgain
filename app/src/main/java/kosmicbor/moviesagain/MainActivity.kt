package kosmicbor.moviesagain

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kosmicbor.moviesagain.ui.mainscreen.MainScreenFragment
import kosmicbor.moviesagain.ui.moviedescriptionscreen.MovieDescriptionScreenFragment

class MainActivity : AppCompatActivity(), MainScreenFragment.OpenFragmentController {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .addToBackStack("mainscreen")
                .replace(R.id.main_container, MainScreenFragment())
                .commit()
        }
    }

    override fun openMovieDescriptionFragment(bundle: Bundle) {
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .addToBackStack("movieDescriptionScreen")
            .replace(R.id.main_container, MovieDescriptionScreenFragment.newInstance(bundle))
            .commit()
    }
}