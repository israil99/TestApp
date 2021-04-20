package kg.izapp.testapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kg.izapp.testapp.data.Event
import kg.izapp.testapp.data.TestApiHelper

class LoginViewModel : ViewModel() {

    private val apiHelper = TestApiHelper()
    var event: MutableLiveData<Event> = MutableLiveData()

    fun checkUserData(login: String, password: String) {
        when (apiHelper.isValidData(login, password)) {
            true -> event.value = Event.SuccessLogin()
            false -> event.value = Event.InCorrectData()
        }
    }
}