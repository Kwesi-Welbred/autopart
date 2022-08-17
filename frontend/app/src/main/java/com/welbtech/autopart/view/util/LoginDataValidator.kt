import android.util.Patterns

object UserDataValidator {

    sealed class Result {
        object Success : Result()
        class Error(val message: String) : Result()
    }

    fun checkEmail(email: String): Result {
        return if (email.contains('@')) {
            if (isEmailValid(email)) Result.Success else Result.Error("Email is not valid")
        } else {
            if (email.isNotBlank()) Result.Success else Result.Error("Email is blank")
        }
    }

    fun checkPassword(password: String): Result {
        return if (password.length > 5) Result.Success else Result.Error("Password must be >5 characters")
    }

    private fun isEmailValid(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()
}