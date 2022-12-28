package router

sealed class ScreenState {
    object RegisterState : ScreenState()

    object Login : ScreenState()

    object Home : ScreenState()
    object Idle : ScreenState()
}
