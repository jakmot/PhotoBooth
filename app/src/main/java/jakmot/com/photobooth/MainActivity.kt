package jakmot.com.photobooth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jakmot.com.photobooth.home.HomeFragment

class MainActivity : AppCompatActivity() {

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