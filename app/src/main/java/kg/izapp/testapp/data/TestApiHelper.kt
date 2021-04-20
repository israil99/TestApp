package kg.izapp.testapp.data

import androidx.lifecycle.MutableLiveData

class TestApiHelper {

    var event: MutableLiveData<Event> = MutableLiveData()

    fun isValidData(login: String, password: String) {
        try {
            Thread.sleep(3000)
            when (login == "test" && password == "12345") {
                true -> event.value = Event.SuccessLogin()
                false -> event.value = Event.InCorrectData()
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}