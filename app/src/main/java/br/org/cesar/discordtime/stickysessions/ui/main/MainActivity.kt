package br.org.cesar.discordtime.stickysessions.ui.main

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.GravityCompat
import br.org.cesar.discordtime.stickysessions.R
import br.org.cesar.discordtime.stickysessions.ui.meeting.MeetingFragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_app_bar.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mToolbarTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureToolbar()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, MeetingFragment()).commit()
    }

    private fun configureToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        mToolbarTitle = findViewById(R.id.toolbar_title)
        mToolbarTitle.apply {
            text = getString(R.string.nav_header_desc)
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        )

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.drawer_menu, menu)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            val icon = menu.findItem(R.id.notification_menu).icon
            val drawable = DrawableCompat.wrap(icon)
            DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.coral))
            menu.findItem(R.id.notification_menu).apply {
                setIcon(drawable)
            }
        }

        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_meetings -> { showMeetingFragment() }
            R.id.nav_about -> { }
            R.id.nav_settings -> { }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showMeetingFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, MeetingFragment()).commit()
    }

}
