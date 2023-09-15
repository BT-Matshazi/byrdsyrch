package byrd.syrch.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import byrd.syrch.R
import android.app.Activity
import android.net.Uri
import android.widget.VideoView
import android.content.Intent
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import byrd.syrch.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class AuthOptions : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_auth_options)
        //declaring the video view
        val welcomeVideo = findViewById<VideoView>(R.id.welcomeVideo)

        //getting the video from raw folder
        val path = "android.resource://" + packageName + "/" + R.raw.welcome

        //setting the video view path
        welcomeVideo.setVideoURI(Uri.parse(path))

        //setting a video completion listener
        welcomeVideo.setOnCompletionListener {
            // Restart the video when it completes
            welcomeVideo.start()
        }

        auth = FirebaseAuth.getInstance()

        if(auth.currentUser != null){
            val intent = Intent(this, byrd.syrch.MainActivity::class.java)
            startActivity(intent)
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        findViewById<Button>(R.id.button3).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.gSignInBtn).setOnClickListener {
            signInGoogle()
        }

        findViewById<Button>(R.id.eSignInBtn).setOnClickListener {
            //redirect to sign up page
            Toast.makeText(this,"email btn clicked", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, byrd.syrch.auth.SignIn::class.java)
            startActivity(intent)
        }
        //starting the video and looping it indefinitely
        welcomeVideo.start()
    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK){

            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if (account != null){
                updateUI(account)
            }
        }else{
            Toast.makeText(this, task.exception.toString() , Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken , null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful){
                val intent : Intent = Intent(this , byrd.syrch.MainActivity::class.java)
                intent.putExtra("email" , account.email)
                intent.putExtra("name" , account.displayName)
                startActivity(intent)
            }else{
                Toast.makeText(this, it.exception.toString() , Toast.LENGTH_SHORT).show()
            }
        }
    }
}

