package playground.jenuine

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

import kotlinx.android.synthetic.main.activity_home.*



class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

    }

    fun openBrowser(view: View) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = android.net.Uri.parse("http://www.google.com")
        startActivity(intent)
    }

}
