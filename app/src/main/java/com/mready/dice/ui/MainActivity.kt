package com.mready.dice.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mready.dice.R
import com.mready.dice.ui.dice.DiceFragment
import com.mready.dice.ui.history.HistoryFragment

class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editPreferences: SharedPreferences.Editor
    var currentId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.container, DiceFragment.newInstance(), DiceFragment::class.simpleName)
            .commit()
        sharedPreferences = this.getSharedPreferences("diceRollData4", Context.MODE_PRIVATE)
        editPreferences = sharedPreferences.edit()
        currentId = sharedPreferences.getInt("currentId", 0)
    }

    fun navigateToHistory() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                HistoryFragment.newInstance(),
                HistoryFragment::class.simpleName
            )
            .addToBackStack(HistoryFragment::class.simpleName)
            .commit()
    }

    fun navigateBack() {
        supportFragmentManager.popBackStackImmediate()
    }

    fun saveDiceValues(value1: Int, value2: Int) {
        val diceSum = value1 + value2
        val diceResult = "$value1-$value2"
        editPreferences.putString("sum$currentId", diceSum.toString())
        editPreferences.putString("result$currentId", diceResult)
        currentId++
        editPreferences.putInt("currentId", currentId)
        editPreferences.apply()
        editPreferences.commit()
    }

    fun getDiceValues(): MutableList<Array<String?>> {
        val diceRollValues: MutableList<Array<String?>> = mutableListOf()
        for (i in currentId - 1 downTo 0 step 1){
            val diceValue: String? = sharedPreferences.getString("result$i", "0-0")
            val diceSum: String? = sharedPreferences.getString("sum$i", "0")
            diceRollValues.add(arrayOf(diceSum, diceValue))
        }
        return diceRollValues
    }


}