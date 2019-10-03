package com.terrymay.asteroidalert.ui.main

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle

import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.terrymay.asteroidalert.R
import com.terrymay.asteroidalert.asteroidApi.NEOClient
import com.terrymay.asteroidalert.ui.adapters.NeoAdapter
import kotlinx.android.synthetic.main.main_fragment.*

import java.time.LocalDate

import java.time.format.DateTimeFormatter


class MainFragment : Fragment() {

    private val neoClient = NEOClient()

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        neoClient.getFeedList(LocalDate.now(), LocalDate.now().plusDays(1))
            .doOnSubscribe {
                txt_loading.visibility = View.VISIBLE
            }
            .doOnComplete {
                txt_loading.visibility = View.GONE
            }
            .subscribe{
                val neos = it.near_earth_objects.toList()[0].second
                neo_list_view.adapter = NeoAdapter(context!!, neos)
            }
    }
}
