package com.rachmad.app.league.ui.team.details

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.rachmad.app.league.R
import com.rachmad.app.league.`object`.TeamData
import kotlinx.android.synthetic.main.fragment_league.view.*
import kotlinx.android.synthetic.main.fragment_team_info.*

private const val ARG_TEAM_INFO = "TeamInfo"

class TeamInfoFragment : Fragment() {
    lateinit var teamData: TeamData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            teamData = it.getSerializable(ARG_TEAM_INFO) as TeamData
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_team_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(teamData) {
            team_name.text = strTeam
            team_description.text = strDescriptionEN
            country.text = strCountry
            stadium_name.text = strStadium
            stadium_location.text = strStadiumLocation
            stadium_capacity.text = intStadiumCapacity
            stadium_description.text = strStadiumDescription
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(teamData: TeamData) =
            TeamInfoFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_TEAM_INFO, teamData)
                }
            }
    }
}
