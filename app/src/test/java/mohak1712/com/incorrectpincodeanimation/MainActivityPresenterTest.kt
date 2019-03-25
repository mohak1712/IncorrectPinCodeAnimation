package mohak1712.com.incorrectpincodeanimation

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainActivityPresenterTest {

    private lateinit var mainPresenter: MainActivityPresenter
    private val mainView = Mockito.mock(MainActivityView::class.java)

    @Before
    fun setUp() {
        mainPresenter = MainActivityPresenter(mainView)
    }

    @Test
    fun shouldCallErrorWhenPasswordIsIncorrect() {
        val expectedPassword = "test123"
        val actualPassword = "test"

        mainPresenter.checkPassword(expectedPassword, actualPassword)

        Mockito.verify(mainView).error("Incorrect Password")
    }
}
