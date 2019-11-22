package com.rachmad.app.league.ui.details

import android.graphics.Typeface
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.tabs.TabLayout
import com.rachmad.app.league.LeagueActivity
import com.rachmad.app.league.GlideApp
import com.rachmad.app.league.R
import com.rachmad.app.league.`object`.MatchList
import com.rachmad.app.league.data.Connection
import com.rachmad.app.league.viewmodel.MatchViewModel
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.design.*
import org.jetbrains.anko.support.v4.viewPager

const val LEAGUE_ID = "id"
class LeagueDetailsActivity : LeagueActivity(), MatchFragment.OnTabListener {
    var idLeague: Int = 0
    val c = this

    lateinit var linearLayout: LinearLayout
    lateinit var backDropImage: ImageView
    lateinit var description: TextView
    lateinit var badge: ImageView
    lateinit var title: TextView
    lateinit var loading: LinearLayout
    lateinit var progressBar: ProgressBar
    lateinit var textError: TextView
    lateinit var textDate: TextView
    lateinit var tabLayout: TabLayout
    lateinit var viewPagerLayout: ViewPager
    lateinit var tabPagerAdapter: TabPagerAdapter
    lateinit var collapsibleLayout: CollapsingToolbarLayout
    var toolbarHeight = -1

    override fun onTabFragmentInteraction(item: MatchList?) {

    }

    val matchViewModel: MatchViewModel by lazy { ViewModelProviders.of(this).get(
        MatchViewModel::class.java)
    }

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

        idLeague = intent.getIntExtra(LEAGUE_ID, 0)

        val connection = viewModel.connectionLeagueDetails()
        viewModel.connectLeagueDetails(idLeague)

        connection.observe(this, Observer<Int> {
            if(checkConnection(it)){
                viewModel.leagueDetails()?.let { data ->
                    with(data) {
                        supportActionBar?.title = HtmlCompat.fromHtml(
                            "<font color='#ffffff'>${strLeague ?: strLeagueAlternate}</font>",
                            HtmlCompat.FROM_HTML_MODE_LEGACY
                        )
                        val backdrop = strFanart1
                            ?: strFanart2
                            ?: strFanart3
                            ?: strFanart4

                        GlideApp.with(backDropImage)
                            .load(backdrop)
                            .centerCrop()
                            .placeholder(R.drawable.no_image)
                            .into(backDropImage)

                        GlideApp.with(badge)
                            .load(strBadge)
                            .fitCenter()
                            .placeholder(R.drawable.no_image)
                            .into(badge)

                        description.text = strDescriptionEN
                        title.text = if (!strLeague.isNullOrBlank())
                            strLeague
                        else
                            strLeagueAlternate

                        textDate.text = dateFirstEvent

                        tabMatch()
                    }
                }
            }
        })
    }

    private fun tabMatch(){
        tabPagerAdapter = TabPagerAdapter(supportFragmentManager, 2)

        viewPagerLayout.adapter = tabPagerAdapter
        viewPagerLayout.offscreenPageLimit = 2
        viewPagerLayout.adapter?.notifyDataSetChanged()

        viewPagerLayout.addOnPageChangeListener(object: TabLayout.TabLayoutOnPageChangeListener(tabLayout) {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })
        tabLayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPagerLayout))
    }

    private fun checkConnection(data: Int?): Boolean{
        data?.let {
            when(it){
                Connection.OK.Status -> {
                    linearLayout.visibility = ViewGroup.VISIBLE
                    loading.visibility = ViewGroup.GONE

                    return true
                }
                Connection.ACCEPTED.Status -> {
                    linearLayout.visibility = ViewGroup.GONE
                    loading.visibility = ViewGroup.VISIBLE
                    progressBar.visibility = ProgressBar.VISIBLE
                    textError.visibility = TextView.GONE
                    return false
                }
                Connection.ERROR.Status -> {
                    linearLayout.visibility = ViewGroup.GONE
                    loading.visibility = ViewGroup.VISIBLE
                    progressBar.visibility = ProgressBar.GONE
                    textError.visibility = TextView.VISIBLE

                    textError.text = viewModel.errorLeagueDetails()?.status_message
                    return false
                }
                else -> {
                    linearLayout.visibility = ViewGroup.GONE
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
        val tv = TypedValue()
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            toolbarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }
        Log.d("main", "Toolbar Height : " + toolbarHeight)

        linearLayout = findViewById(main)
        backDropImage = findViewById(backDrop)
        description = findViewById(descriptionLeague)
        badge = findViewById(logo)
        title = findViewById(titleLeague)
        loading = findViewById(loadingLayout)
        progressBar = findViewById(circle)
        textError = findViewById(errorText)
        tabLayout = findViewById(tab)
        textDate = findViewById(date)
        viewPagerLayout = findViewById(viewPager)
        collapsibleLayout = findViewById(collapsible)

        val collapsible = collapsibleLayout.layoutParams as AppBarLayout.LayoutParams
        val tab = tabLayout.layoutParams as AppBarLayout.LayoutParams
        val pager = viewPagerLayout.layoutParams as CollapsingToolbarLayout.LayoutParams
        val pager2 = viewPagerLayout.layoutParams as CoordinatorLayout.LayoutParams

        collapsible.setMargins(0,0,0, toolbarHeight * -1)
        collapsible.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
        tab.setMargins(0, toolbarHeight + 1, 0, 0)
        pager.collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX
        pager2.behavior = AppBarLayout.ScrollingViewBehavior()

        collapsibleLayout.requestLayout()
        tabLayout.requestLayout()
        viewPagerLayout.requestLayout()
    }

    inner class TabPagerAdapter(fm: FragmentManager, val countTabs: Int): FragmentStatePagerAdapter(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
        override fun getItem(position: Int): Fragment {
            Log.d("main", "POSITION : " + position)
            return MatchFragment().apply {
                arguments = Bundle().apply {
                    putInt(MatchFragment.ARG_POSITION, position)
                    putInt(MatchFragment.ARG_ID, idLeague)
                }
            }
        }

        override fun getCount(): Int = countTabs
    }

    inner class UI: AnkoComponent<LeagueDetailsActivity> {
        override fun createView(ui: AnkoContext<LeagueDetailsActivity>): View = with(ui){
//            var coordinatorParams: CoordinatorLayout.LayoutParams
//            var appbarParam: AppBarLayout.LayoutParams
//            var collapsingParam: CollapsingToolbarLayout.LayoutParams

            frameLayout {
                lparams(matchParent, matchParent)

                verticalLayout {
                    id = main
                    lparams(matchParent, matchParent)

                    coordinatorLayout {
                        lparams{
                            width = matchParent
                            height = matchParent
                        }
                        fitsSystemWindows = false
                        backgroundColor = ContextCompat.getColor(c, R.color.white)
//                        coordinatorParams = layoutParams as CoordinatorLayout.LayoutParams
//                        coordinatorParams.behavior = AppBarLayout.ScrollingViewBehavior()

                        appBarLayout {
                            lparams(matchParent, wrapContent)
                            themedAppBarLayout(R.style.AppTheme_AppBarOverlay)
//                            appbarParam = layoutParams as AppBarLayout.LayoutParams
//                            appbarParam.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
//                            requestLayout()

                            collapsingToolbarLayout {
                                id = collapsible
                                lparams{
                                    width = matchParent
                                    height = wrapContent
                                }

                                fitsSystemWindows = true
                                expandedTitleMarginEnd = dip(64)
                                expandedTitleMarginStart = dip(48)
                                setExpandedTitleTextAppearance(android.R.color.transparent)

//                                collapsingParam = layoutParams as CollapsingToolbarLayout.LayoutParams
//                                collapsingParam.collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX
//                                requestLayout()

                                cardView {
                                    elevation = dip(1).toFloat()
                                    radius = dip(8).toFloat()
                                    useCompatPadding = true

                                    verticalLayout {
                                        lparams(matchParent, wrapContent)

                                        imageView {
                                            id = backDrop
                                            adjustViewBounds = true
                                            scaleType = ImageView.ScaleType.CENTER_CROP
                                        }.lparams {
                                            width = dip(matchParent)
                                            height = dip(300)
                                            margin = dip(8)
                                        }

                                        linearLayout {
                                            lparams(matchParent, wrapContent)
                                            orientation = LinearLayout.HORIZONTAL

                                            verticalLayout {
                                                lparams(wrapContent, wrapContent)

                                                imageView {
                                                    id = logo
                                                    adjustViewBounds = true
                                                    scaleType = ImageView.ScaleType.CENTER_CROP
                                                }.lparams {
                                                    width = dip(100)
                                                    height = dip(100)
                                                    margin = dip(8)
                                                }

                                                textView {
                                                    id = date
                                                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                                                }.lparams(dip(100), wrapContent){
                                                    margin = dip(8)
                                                }
                                            }

                                            verticalLayout {
                                                lparams(matchParent, wrapContent, 1F)

                                                textView {
                                                    id = titleLeague
                                                    textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                                                    setTypeface(Typeface.DEFAULT_BOLD)
                                                }.lparams {
                                                    width = matchParent
                                                    height = wrapContent
                                                    leftMargin = dip(8)
                                                    topMargin = dip(8)
                                                }

                                                textView {
                                                    id = descriptionLeague
                                                    textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                                                    lines = 5
                                                    ellipsize = TextUtils.TruncateAt.END
                                                }.lparams {
                                                    width = matchParent
                                                    height = wrapContent
                                                    margin = dip(8)
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            tabLayout {
                                id = tab
                                lparams{
                                    width = matchParent
                                    height = wrapContent
                                    topMargin = dip(2)
                                }
                                backgroundColor = ContextCompat.getColor(c, R.color.white)
                                setSelectedTabIndicatorColor(ContextCompat.getColor(c, R.color.colorPrimary))
                                tabMode = TabLayout.MODE_SCROLLABLE
                                setTabTextColors(ContextCompat.getColor(c, R.color.gray), ContextCompat.getColor(c, R.color.black))
//                                tabGravity = TabLayout.GRAVITY_FILL

                                addTab(newTab().setText(getString(R.string.last_match)))
                                addTab(newTab().setText(getString(R.string.next_match)))
                            }
                        }

                        viewPager {
                            id = viewPager
                            backgroundColor = ContextCompat.getColor(c, R.color.white)
                        }.lparams{
                            width = matchParent
                            height = matchParent
                            topMargin = dip(4)
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
                    backgroundColor = ContextCompat.getColor(this.context, R.color.white)

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
        val viewPager = 2009
        val date = 2010
        val tab = 2011
        val collapsible = 2012
    }
}
