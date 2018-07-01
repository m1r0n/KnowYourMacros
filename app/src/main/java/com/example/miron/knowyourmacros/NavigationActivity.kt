package com.example.miron.knowyourmacros

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.app_bar_navigation.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class NavigationActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var macroSplit: HashMap<String, Double>
    private lateinit var pieChart: PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        initializeValuesFromIntent()
        createChart()
        initializeTextViews()

        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun initializeTextViews() {
        val proteinText: TextView = findViewById(R.id.tv_protein_value)
        val carbText: TextView = findViewById(R.id.tv_carbs_value)
        val fatText: TextView = findViewById(R.id.tv_fats_value)

        proteinText.text = macroSplit["protein"].toString()
        carbText.text = macroSplit["carbs"].toString()
        fatText.text = macroSplit["fats"].toString()
    }

    private fun createChart() {
        pieChart = findViewById(R.id.idPieChart)
        pieChart.holeRadius = 90f
        pieChart.isRotationEnabled = true
        pieChart.description.isEnabled = false

        addHoleToChart()

        addDataSetToChart()
        disableLegend()
        pieChart.invalidate()
    }

    private fun addHoleToChart() {
        addCustomDrawingToHole()
        pieChart.setHoleColor(ResourcesCompat.getColor(resources, R.color.colorWindowBackground, null))
        pieChart.centerText = (macroSplit["calories"]!!.toInt()).toString() + " kcal"
        pieChart.setCenterTextOffset(0f, 35f)
        pieChart.setCenterTextColor(ResourcesCompat.getColor(resources, R.color.colorPrimaryButton, null))
        pieChart.setCenterTextSize(25f)
    }

    private fun addCustomDrawingToHole() {
        pieChart.renderer = CustomPieChartRenderer(pieChart, pieChart.animator, pieChart.viewPortHandler)
    }

    private fun addDataSetToChart() {
        val yEntries: ArrayList<PieEntry> = createEntries()
        val pieDataSet = PieDataSet(yEntries, "")
        pieDataSet.sliceSpace = 2f
        pieDataSet.colors = Arrays.asList(
                ResourcesCompat.getColor(resources, R.color.colorGraphYellow, null), //carbs
                ResourcesCompat.getColor(resources, R.color.textColorPrimary, null), //protein
                ResourcesCompat.getColor(resources, R.color.colorPrimaryButton, null)) //fats


        pieDataSet.setDrawValues(false)
        val pieData = PieData(pieDataSet)
        pieChart.data = pieData
    }

    private fun createEntries(): ArrayList<PieEntry> {
        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry(macroSplit["carbs"]!!.toFloat()))
        entries.add(PieEntry(macroSplit["protein"]!!.toFloat()))
        entries.add(PieEntry(macroSplit["fats"]!!.toFloat()))
        return entries
    }

    private fun disableLegend() {
        val legend: Legend = pieChart.legend
        legend.isEnabled = false
    }

    private fun initializeValuesFromIntent() {
        val intent: Intent = intent
        val preferences: HashMap<String, Answer> = intent.getSerializableExtra("preferences") as HashMap<String, Answer>
        macroSplit = makeAPIRequest(preferences)


        //Here are some random values for testing this activity
        /*
        macroSplit = HashMap()
        macroSplit["calories"] = 2450.0
        macroSplit["protein"] = 150.0
        macroSplit["fats"] = 100.0
        macroSplit["carbs"] = 250.0
        */
    }

    private fun makeAPIRequest(userPreferences: HashMap<String, Answer>): HashMap<String, Double> {
        val requestMaker = RequestMaker(Values.api_url, userPreferences)
        return requestMaker.execute("").get()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.navigation, menu)
        val doneButton: MenuItem = menu.findItem(R.id.preferences_done_button)
        doneButton.isVisible = false

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.preferences_done_button -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.macros -> {
                // Handle the camera action
            }
            R.id.preferences -> {
                val intent = Intent(this, UserChoiceActivity::class.java)
                startActivity(intent)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
