package com.example.miron.knowyourmacros

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.app_bar_navigation.*
import java.lang.reflect.Array
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

        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun createChart() {
        pieChart = findViewById(R.id.idPieChart)
        pieChart.holeRadius = 80f
        pieChart.isRotationEnabled = true
        pieChart.setHoleColor(ResourcesCompat.getColor(resources, R.color.colorWindowBackground, null))
        //pieChart.setDrawEntryLabels(false)
        addDataSetToChart()
        addLegendToChart()
        pieChart.invalidate()
    }

    private fun addDataSetToChart() {
        val yEntrys: ArrayList<PieEntry> = ArrayList()
        val xEntrys: ArrayList<String> = ArrayList()

        for ((key, value) in macroSplit) {
            xEntrys.add(key)
            yEntrys.add(PieEntry(value.toFloat()))
        }
        val pieDataSet = PieDataSet(yEntrys, "MACROS")
        pieDataSet.sliceSpace = 2f
        pieDataSet.colors = Arrays.asList(
                ResourcesCompat.getColor(resources, R.color.colorGraphYellow, null), //carbs
                ResourcesCompat.getColor(resources, R.color.colorPrimaryButton, null), //fats
                ResourcesCompat.getColor(resources, R.color.textColorPrimary, null)) //protein

        pieDataSet.setDrawValues(false)
        val pieData = PieData(pieDataSet)
        pieChart.data = pieData
    }

    private fun addLegendToChart() {
        val legend: Legend = pieChart.legend
        legend.isEnabled = false

        /*
        legend.maxSizePercent = 0.8f
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.orientation = Legend.LegendOrientation.VERTICAL
        legend.setDrawInside(false);

        val colors = arrayOf(
                ResourcesCompat.getColor(resources, R.color.colorGraphYellow, null),
                ResourcesCompat.getColor(resources, R.color.colorPrimaryButton, null),
                ResourcesCompat.getColor(resources, R.color.textColorPrimary, null))

        val labels = arrayOf("carbohydrates", "fats", "proteins")


        val legendEntryList: ArrayList<LegendEntry> = ArrayList()
        for (i in colors.indices) {
            legendEntryList.add(LegendEntry(labels[i], Legend.LegendForm.DEFAULT, Float.NaN, Float.NaN, null, colors[i]))
        }
        legend.setCustom(legendEntryList)
        */
    }

    private fun initializeValuesFromIntent() {
        val intent: Intent = intent

        //TODO: COMMENT IN THIS BLOCK WHEN IMPLEMENTATION COMPLETE
        //These are some random values
        macroSplit = HashMap()
        macroSplit["protein"] = 150.0
        macroSplit["fats"] = 100.0
        macroSplit["carbs"] = 250.0

        //TODO: UNCOMMENT WHEN DONE
        //this is real macrosplit
        //macroSplit = intent.getSerializableExtra("macrosplit") as HashMap<String, Double>
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.navigation, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.preferences_done_button -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
