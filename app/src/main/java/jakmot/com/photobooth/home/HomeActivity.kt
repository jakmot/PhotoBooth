package jakmot.com.photobooth.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jakmot.com.photobooth.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_activity)

        val homeFragment = HomeFragment()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.content, homeFragment)
            .commit()
    }
}