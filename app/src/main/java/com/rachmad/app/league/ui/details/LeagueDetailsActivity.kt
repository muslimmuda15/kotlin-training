package com.rachmad.app.league.ui.details

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.text.HtmlCompat
import androidx.core.view.marginTop
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.rachmad.app.league.BaseActivity
import com.rachmad.app.league.GlideApp
import com.rachmad.app.league.R
import com.rachmad.app.league.data.Connection
import com.rachmad.app.league.ui.MainActivity
import org.jetbrains.anko.*

const val LEAGUE_ID = "id"
class LeagueDetailsActivity : BaseActivity() {
    var id: Int = 0

    lateinit var scrollView: ScrollView
    lateinit var backDropImage: ImageView
    lateinit var description: TextView
    lateinit var badge: ImageView
    lateinit var title: TextView
    lateinit var loading: LinearLayout
    lateinit var progressBar: ProgressBar
    lateinit var textError: TextView

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        UI().setContentView(this)
        initialize()

        id = intent.getIntExtra(LEAGUE_ID, 0)

        val connection = viewModel.connectionLeagueDetails()
        viewModel.connectLeagueDetails(id)

        connection.observe(this, Observer<Int> {
            if(checkConnection(it)){
                viewModel.leagueDetails()?.let { data ->
                    supportActionBar?.title = HtmlCompat.fromHtml("<font color='#ffffff'>${data.strLeagueAlternate}</font>", HtmlCompat.FROM_HTML_MODE_LEGACY)

                    GlideApp.with(backDropImage)
                        .load(data.strFanart3)
                        .centerCrop()
                        .placeholder(R.drawable.no_image)
                        .into(backDropImage)

                    GlideApp.with(badge)
                        .load(data.strBadge)
                        .fitCenter()
                        .placeholder(R.drawable.no_image)
                        .into(badge)

                    description.text = data.strDescriptionEN
                    title.text = data.strLeagueAlternate
                }
            }
        })
    }

    private fun checkConnection(data: Int?): Boolean{
        data?.let {
            when(it){
                Connection.OK.Status -> {
                    scrollView.visibility = ViewGroup.VISIBLE
                    loading.visibility = ViewGroup.GONE

                    return true
                }
                Connection.ACCEPTED.Status -> {
                    scrollView.visibility = ViewGroup.GONE
                    loading.visibility = ViewGroup.VISIBLE
                    progressBar.visibility = ProgressBar.VISIBLE
                    textError.visibility = TextView.GONE
                    return false
                }
                Connection.ERROR.Status -> {
                    scrollView.visibility = ViewGroup.GONE
                    loading.visibility = ViewGroup.VISIBLE
                    progressBar.visibility = ProgressBar.GONE
                    textError.visibility = TextView.VISIBLE

                    textError.text = viewModel.errorLeagueDetails()?.status_message
                    return false
                }
                else -> {
                    scrollView.visibility = ViewGroup.GONE
                    loading.visibility = ViewGroup.VISIBLE
                    progressBar.visibility = ProgressBar.GONE
                    textError.visibility = TextView.VISIBLE

                    textError.text = getString(R.string.unknown_error)
                    return false
                }
            }
        } ?: run {
            return false
        }
    }

    private fun initialize(){
        scrollView = findViewById(main)
        backDropImage = findViewById(backDrop)
        description = findViewById(descriptionLeague)
        badge = findViewById(logo)
        title = findViewById(titleLeague)
        loading = findViewById(loadingLayout)
        progressBar = findViewById(circle)
        textError = findViewById(errorText)
    }

    inner class UI: AnkoComponent<LeagueDetailsActivity> {
        override fun createView(ui: AnkoContext<LeagueDetailsActivity>): View = with(ui){
            frameLayout {
                lparams(matchParent, matchParent)

                scrollView {
                    id = main
                    lparams(matchParent, wrapContent)

                    relativeLayout {
                        lparams(matchParent, matchParent)

                        imageView {
                            id = backDrop
                            adjustViewBounds = true
                            scaleType = ImageView.ScaleType.CENTER_CROP
                        }.lparams {
                            width = matchParent
                            height = dip(200)
                        }

                        verticalLayout {
                            gravity = Gravity.CENTER_HORIZONTAL

                            imageView {
                                id = logo
                                adjustViewBounds = true
                                scaleType = ImageView.ScaleType.FIT_CENTER
                            }.lparams{
                                width = dip(150)
                                height = dip(150)
                            }

                            textView {
                                id = titleLeague
                                textAlignment = View.TEXT_ALIGNMENT_CENTER
                                setTypeface(Typeface.DEFAULT_BOLD)
                            }.lparams {
                                width = dip(150)
                                height = wrapContent
                            }

                            textView{
                                text = getString(R.string.description)
                                setTypeface(Typeface.DEFAULT_BOLD)
                                setTextSize(16F)
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                topMargin = dip(12)
                                leftMargin = dip(8)
                                rightMargin = dip(8)
                            }

                            textView {
                                id = descriptionLeague
                                textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                topMargin = dip(8)
                                leftMargin = dip(8)
                                rightMargin = dip(8)
                            }
                        }.lparams {
                            topMargin = dip(125)
                            width = matchParent
                            height = wrapContent
                            centerHorizontally()
                        }
                    }
                }

                verticalLayout {
                    id = loadingLayout
                    lparams(matchParent, matchParent)
                    gravity = Gravity.CENTER
                    elevation = 2.0F
                    isFocusable = true
                    isClickable = true

                    progressBar {
                        id = circle
                        visibility = View.VISIBLE
                    }.lparams(width = wrapContent, height = wrapContent)

                    textView {
                        id = errorText
                    }
                }
            }
        }
    }

    companion object{
        val backDrop = 2001
        val logo = 2002
        val titleLeague = 2003
        val descriptionLeague = 2004
        val loadingLayout = 2005
        val circle = 2006
        val errorText = 2007
        val main = 2008
    }
}
