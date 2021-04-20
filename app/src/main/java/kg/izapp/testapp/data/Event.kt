package kg.izapp.testapp.data

sealed class  Event() {
    class SuccessLogin : Event()
    class InCorrectData : Event()
}
