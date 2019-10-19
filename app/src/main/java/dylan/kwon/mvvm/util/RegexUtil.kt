package dylan.kwon.mvvm.util

import java.util.regex.Pattern

class RegexUtil {

    companion object {
        const val REGEX_IDENTIFY = "^[a-zA-Z][a-zA-Z0-9_.\\-]{5,19}\$"
        const val REGEX_EMAIL = "^.+@.+\\..+$"
        const val REGEX_PHONE = "^01\\d{1}\\d{3,4}\\d{4}$"
        const val REGEX_PASSWORD = "^(?=.*[a-zA-Z])((?=.*\\d)|(?=.*\\W)).{10,20}\$|^(?=.*\\d)((?=.*[a-zA-Z])|(?=.*\\W)).{10,20}\$|^(?=.*\\W)((?=.*[a-zA-Z])|(?=.*\\d)).{10,20}\$"
        const val REGEX_BIRTH_DAY = "^(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[0-1])$"
    }

    fun checkIdentify(identify: String?): Boolean {
        if (identify.isNullOrEmpty()) {
            return false
        }
        return Pattern.compile(REGEX_IDENTIFY)
            .matcher(identify)
            .matches()
    }

    fun checkEmail(email: String?): Boolean {
        if (email.isNullOrEmpty()) {
            return false
        }
        return Pattern.compile(REGEX_EMAIL)
            .matcher(email)
            .matches()
    }

    fun checkPhone(phone: String?): Boolean {
        if (phone.isNullOrEmpty()) {
            return false
        }
        return Pattern.compile(REGEX_PHONE)
            .matcher(phone)
            .matches()
    }

    fun checkPassword(password: String?): Boolean {
        if (password.isNullOrEmpty()) {
            return false
        }
        return Pattern.compile(REGEX_PASSWORD)
            .matcher(password)
            .matches()
    }

    fun checkBirthDay(birthDay: String?): Boolean {
        if (birthDay.isNullOrEmpty()) {
            return false
        }
        return Pattern.compile(REGEX_BIRTH_DAY)
            .matcher(birthDay)
            .matches()
    }

}
