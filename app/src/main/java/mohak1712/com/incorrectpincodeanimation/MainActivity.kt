package mohak1712.com.incorrectpincodeanimation

import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.ChangeBounds
import androidx.transition.Transition
import androidx.transition.TransitionManager
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainActivityView {

    private lateinit var mainActivityPresenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainActivityPresenter = MainActivityPresenter(this)
        pin_four.setOnEditorActionListener { _, actionId, _ ->
            comparePasswordOnCorrectAction(actionId)
        }
    }

    private fun comparePasswordOnCorrectAction(actionId: Int): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            if (!pinIsEmpty()) {
                enableEditTexts(false)
                setDrawableForUnderlineViews(R.drawable.line_to_wrong_pass_anim)
                mainActivityPresenter.checkPassword(getEnteredPin(), "actualPassword")
            }
        }
        return false
    }

    private fun enableEditTexts(enable: Boolean) {
        pin_one.isEnabled = enable
        pin_two.isEnabled = enable
        pin_three.isEnabled = enable
        pin_four.isEnabled = enable
    }

    private fun pinIsEmpty() = (TextUtils.isEmpty(pin_one.text) || TextUtils.isEmpty(pin_two.text)
            || TextUtils.isEmpty(pin_three.text) || TextUtils.isEmpty(pin_four.text))

    private fun getEnteredPin(): String {
        return pin_one.text.toString()
            .plus(pin_two.text.toString())
            .plus(pin_three.text.toString())
            .plus(pin_four.text.toString())
    }

    private fun setDrawableForUnderlineViews(layout: Int) {
        val animatedVectorDrawable = AnimatedVectorDrawableCompat
            .create(this@MainActivity, layout)
        view1.setImageDrawable(animatedVectorDrawable)
        view2.setImageDrawable(animatedVectorDrawable)
        view3.setImageDrawable(animatedVectorDrawable)
        view4.setImageDrawable(animatedVectorDrawable)
    }

    override fun error(response: String) {
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
        joinUnderlineViews()
    }

    private fun joinUnderlineViews() {
        val constraintSet = getConstraintSet(R.layout.joined_underline_views)
        val transition = getTransition(1200)
        setUpListenersForTransition(transition, { enableEditTexts(false) }) {
            joinPinTexts()
        }
        TransitionManager.beginDelayedTransition(root, transition)
        constraintSet.applyTo(root)
    }

    private fun getConstraintSet(layout: Int): ConstraintSet {
        val constrainSet = ConstraintSet()
        constrainSet.clone(this, layout)
        return constrainSet
    }

    private fun getTransition(animationDuration: Long): Transition {
        val changeBoundsTransition = ChangeBounds()
        changeBoundsTransition.duration = animationDuration
        changeBoundsTransition.interpolator = AccelerateDecelerateInterpolator()
        return changeBoundsTransition
    }

    private fun setUpListenersForTransition(transition: Transition, startListener: () -> Unit, endListener: () -> Unit) {
        transition.addListener(TransitionListener({ startListener() }, { endListener() }))
    }

    private fun joinPinTexts() {
        val constraintSet = getConstraintSet(R.layout.joined_texts)
        val joinEditText = getTransition(500)
        setUpListenersForTransition(joinEditText, { enableEditTexts(false) }) {
            startAnimationForUnderlineViews()
            setUpListenerForUnderlineViewsAnimation(false)
        }
        TransitionManager.beginDelayedTransition(root, joinEditText)
        constraintSet.applyTo(root)
    }

    private fun startAnimationForUnderlineViews() {
        (view1.drawable as Animatable).start()
        (view2.drawable as Animatable).start()
        (view3.drawable as Animatable).start()
        (view4.drawable as Animatable).start()
    }

    private fun setUpListenerForUnderlineViewsAnimation(reverseToInitialState: Boolean) {
        (view4.drawable as AnimatedVectorDrawableCompat).registerAnimationCallback(object :
            Animatable2Compat.AnimationCallback() {
            override fun onAnimationEnd(drawable: Drawable?) {
                if (!reverseToInitialState) {
                    setDrawableForUnderlineViews(R.drawable.wrong_pass_to_line_anim)
                    startAnimationForUnderlineViews()
                    setUpListenerForUnderlineViewsAnimation(!reverseToInitialState)
                } else {
                    backToInitialState()
                }
            }
        })
    }

    private fun backToInitialState() {
        val transition = getTransition(500)
        val constraintSet = getConstraintSet(R.layout.activity_main)
        setUpListenersForTransition(transition, { enableEditTexts(false) }) {
            enableEditTexts(true)
        }
        TransitionManager.beginDelayedTransition(root, transition)
        constraintSet.applyTo(root)
    }
}
