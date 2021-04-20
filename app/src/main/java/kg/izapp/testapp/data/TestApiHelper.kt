package kg.izapp.testapp.data


class TestApiHelper {

    fun isValidData(login: String, password: String): Boolean {
        return login == "test" && password == "12345"
    }
}