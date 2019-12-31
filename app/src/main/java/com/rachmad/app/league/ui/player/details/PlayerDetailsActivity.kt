package com.rachmad.app.league.ui.player.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.text.HtmlCompat
import com.rachmad.app.league.GlideApp
import com.rachmad.app.league.R
import com.rachmad.app.league.`object`.PlayerData
import kotlinx.android.synthetic.main.activity_player_details.*

const val PLAYER_DATA = "PlayerData"
class PlayerDetailsActivity : AppCompatActivity() {
    lateinit var playerData: PlayerData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_details)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.elevation = 0F

        playerData = intent.getSerializableExtra(PLAYER_DATA) as PlayerData

        supportActionBar?.title = HtmlCompat.fromHtml(
            "<font color='#ffffff'>${playerData.strPlayer ?: "" }</font>",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        with(playerData) {
            GlideApp.with(player_image)
                .load(playerData.strCutout)
                .centerCrop()
                .into(player_image)

            name.text = strPlayer
            position.text = strPosition
            weight.text = strWeight
            height.text = strHeight
            birth_location.text = strBirthLocation
            birth_date.text = dateBorn
            description.text = strDescriptionEN
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
