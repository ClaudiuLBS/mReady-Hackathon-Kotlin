package com.mready.dice.ui.dice

import android.animation.Animator
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.mready.dice.R
import com.mready.dice.ui.MainActivity
import com.mready.dice.ui.dialog.DoubleDialog

class DiceFragment : Fragment() {

    companion object {
        fun newInstance(): DiceFragment {
            return DiceFragment()
        }
    }

    private lateinit var containerHistory: View
    private lateinit var rollButton: Button
    private lateinit var firstDice: ImageView
    private lateinit var secondDice: ImageView
    private lateinit var firstDiceAnim: LottieAnimationView
    private lateinit var secondDiceAnim: LottieAnimationView
    private lateinit var lastRollValueText: TextView

    private var firstDiceValue: Int? = null
    private var secondDiceValue: Int? = null

    private val animatorListener:Animator.AnimatorListener = object: Animator.AnimatorListener {
        override fun onAnimationStart(p0: Animator?) { showAnimation() }
        override fun onAnimationEnd(p0: Animator?) { hideAnimation() }
        override fun onAnimationCancel(p0: Animator?) { }
        override fun onAnimationRepeat(p0: Animator?) { }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dice, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getViewsReferences(view)

        containerHistory.setOnClickListener { (requireActivity() as MainActivity).navigateToHistory() }
        rollButton.setOnClickListener { rollDice() }
        firstDiceAnim.addAnimatorListener(animatorListener)
        val diceValues = (requireActivity() as MainActivity).getDiceValues()

        if (diceValues.size > 1)
            lastRollValueText.text = diceValues[0][1]
    }

    private fun getViewsReferences(view: View) {
        containerHistory = view.findViewById(R.id.container_history)
        rollButton = view.findViewById(R.id.btn_roll)
        firstDice = view.findViewById(R.id.iv_first_die)
        secondDice = view.findViewById(R.id.iv_second_die)
        firstDiceAnim = view.findViewById(R.id.first_dice_anim)
        secondDiceAnim = view.findViewById(R.id.second_dice_anim)
        lastRollValueText = view.findViewById(R.id.tv_last_roll)
    }

    private fun rollDice() {
        if (firstDiceValue != null) {
            val lastRollText: String = "$firstDiceValue-$secondDiceValue"
            lastRollValueText.text = lastRollText
        }

        firstDiceValue = (1..6).random()
        secondDiceValue = (1..6).random()
        firstDice.setImageResource(pickDiceImage(firstDiceValue!!))
        secondDice.setImageResource(pickDiceImage(secondDiceValue!!))
        firstDiceAnim.playAnimation()
        secondDiceAnim.playAnimation()
        (requireActivity() as MainActivity).saveDiceValues(firstDiceValue!!, secondDiceValue!!)
    }

    private fun pickDiceImage(diceValue: Int): Int {
        return when(diceValue) {
            1 -> R.drawable.dice_face_01
            2 -> R.drawable.dice_face_02
            3 -> R.drawable.dice_face_03
            4 -> R.drawable.dice_face_04
            5 -> R.drawable.dice_face_05
            6 -> R.drawable.dice_face_06
            else -> R.drawable.dice_face_01
        }
    }
    fun hideAnimation() {
        firstDiceAnim.alpha = 0F
        secondDiceAnim.alpha = 0F
        showDoubleDialog()
    }
    fun showAnimation() {
        firstDiceAnim.alpha = 1F
        secondDiceAnim.alpha = 1F
    }

    private fun showDoubleDialog() {
        if (firstDiceValue == secondDiceValue) {
            val dialog = DoubleDialog()
            dialog.show(this.parentFragmentManager, "double_dialog")
            val pattern = longArrayOf(0, 200, 100, 300)
            val vibrator: Vibrator? = (requireActivity() as MainActivity).getSystemService<Vibrator>()
            vibrator!!.vibrate(VibrationEffect.createWaveform(pattern, -1))
        }
    }

}

