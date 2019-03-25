package mohak1712.com.incorrectpincodeanimation

class MainActivityPresenter(private val mainView: MainActivityView) {

    fun checkPassword(expectedPassword: String, actualPassword: String) {
        if (expectedPassword != actualPassword) {
            mainView.error("Incorrect Password")
        }
    }
}
