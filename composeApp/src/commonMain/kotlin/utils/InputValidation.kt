package utils


const val ERR_LEN = "Password must have at least ten characters!"
const val ERR_WHITESPACE = "Password must not contain whitespace"
const val ERR_DIGIT = "Password must contain at least one digit"
const val ERR_UPPER = "Password must have at least one uppercase letter"
data class StrongPasswordValidator(private val pwd: String) {
    fun isValidPassword(): Boolean {
        return  runCatching {
            require(pwd.length >= 8) { ERR_LEN }
            require(pwd.none { it.isWhitespace() }) { ERR_WHITESPACE }
            require(pwd.any { it.isDigit() }) { ERR_DIGIT }
            require(pwd.any { it.isUpperCase() }) { ERR_UPPER }
        }.isSuccess
    }
}


data class InputValidator(private val input: ArrayList<String> = arrayListOf()) {
    fun isValidInput(): Boolean {
        var isValid = false
        for (element in input) {
            if(element.isEmpty()){
                isValid = false
                break
            }
            else{
                isValid = true
            }
        }
        return isValid
    }
}

data class EmailValidator(private val input: String) {

    val emailPattern = "^[A-Za-z](.*)([@])(.+)(\\.)(.+)".toRegex()
    fun isValidEmail(): Boolean {
        return input.isNotEmpty() && emailPattern.matches(input)
    }

}


