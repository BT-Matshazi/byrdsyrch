package byrd.syrch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //getting user id
        auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid

        //checking if user is logged in
        if(auth.currentUser == null){
            val intent = Intent(this, byrd.syrch.auth.AuthOptions::class.java)
            startActivity(intent)
        }

        val mapFragment = Map()
        val settingsFragment = Settings()
        val rareBirdsFragment = RareBirdsList()
        val sightingsFragment = SightingsList()
        val logSightingsFragment = LogSightings()

        setCurrentFragment(mapFragment)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.mapHome->setCurrentFragment(mapFragment)
                R.id.settings->setCurrentFragment(settingsFragment)
                R.id.challenges->setCurrentFragment(rareBirdsFragment)
                R.id.sightings->setCurrentFragment(sightingsFragment)
            }
            true
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            setCurrentFragment(logSightingsFragment)
        }
    }

    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            commit()
        }
    }
}