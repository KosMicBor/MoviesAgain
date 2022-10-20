package kosmicbor.moviesagain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kosmicbor.moviesagain.ui.mainscreen.MainScreenFragment

class MainActivity : AppCompatActivity() {
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
}