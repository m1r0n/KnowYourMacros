package com.example.miron.knowyourmacros

import android.content.Context
import android.graphics.Canvas
import android.support.v4.content.res.ResourcesCompat
import com.github.mikephil.charting.utils.ViewPortHandler
import com.github.mikephil.charting.animation.ChartAnimator
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.renderer.PieChartRenderer


class CustomPieChartRenderer(chart: PieChart, animator: ChartAnimator,
                             viewPortHandler: ViewPortHandler) : PieChartRenderer(chart, animator, viewPortHandler) {
    private val context: Context = chart.context

    override fun drawExtras(c: Canvas) {
        super.drawExtras(c)
        drawImage(c)
    }

    private fun drawImage(c: Canvas) {
        val center = mChart.centerCircleBox

        val d = ResourcesCompat.getDrawable(context.resources, R.drawable.ic_salad, null)

        if (d != null) {
            val halfWidth = (d.intrinsicWidth / 2).toFloat()
            val halfHeight = (d.intrinsicHeight / 2).toFloat()

            d.setBounds((center.x - halfWidth).toInt(), (center.y - halfHeight-70f).toInt(), (center.x + halfWidth).toInt(), (center.y + halfHeight-70f).toInt())
            d.draw(c)
        }
    }
}