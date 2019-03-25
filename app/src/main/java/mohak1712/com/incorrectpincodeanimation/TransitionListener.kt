package mohak1712.com.incorrectpincodeanimation

import androidx.transition.Transition

class TransitionListener(val transitionStart: (Transition) -> Unit, val transitionEnd: (Transition) -> Unit) :
    Transition.TransitionListener {

    override fun onTransitionEnd(transition: Transition) {
        transitionEnd(transition)
    }

    override fun onTransitionResume(transition: Transition) {

    }

    override fun onTransitionPause(transition: Transition) {

    }

    override fun onTransitionCancel(transition: Transition) {

    }

    override fun onTransitionStart(transition: Transition) {
        transitionStart(transition)
    }
}
