package mohak1712.com.incorrectpincodeanimation

import android.os.Bundle
import android.text.TextUtils
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
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

    }
}
