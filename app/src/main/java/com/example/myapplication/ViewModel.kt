package com.example.myapplication

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.util.User
import com.example.myapplication.util.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository,
    application: Application
) : ViewModel() {
    private val prefs = application.getSharedPreferences("user_guide_prefs", Context.MODE_PRIVATE)

    val users = repository.allUsers
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    val showFirstSaveGuide = mutableStateOf(false)
    val showGuideClick = mutableStateOf(false)
    val showGuideLongPress = mutableStateOf(false)
    val showGuideClickClearAll = mutableStateOf(false)

    var firstName = mutableStateOf("")
    var lastName = mutableStateOf("")
    var eMail = mutableStateOf("")

    init {
        val isFirstTimeOnUserPage = prefs.getBoolean("is_first_time_on_user_page", true)
        if (isFirstTimeOnUserPage) {
            showGuideClick.value = true
        }
    }

    fun addUser() {
        viewModelScope.launch {
            val newUser = User(
                firstName = firstName.value.trim(),
                lastName = lastName.value.trim(),
                eMail = eMail.value.trim()
            )
            if (newUser.firstName.isNotBlank() && newUser.lastName.isNotBlank()) {
                repository.addUser(newUser)
                resetFields()

                val hasAlreadyShownFirstSaveGuide = prefs.getBoolean("has_shown_first_save_guide", false)
                if (!hasAlreadyShownFirstSaveGuide) {
                    showFirstSaveGuide.value = true
                    with(prefs.edit()) {
                        putBoolean("has_shown_first_save_guide", true)
                        apply()
                    }
                }
            }
        }
    }

    fun dismissFirstSaveGuide() {
        showFirstSaveGuide.value = false
    }

    fun onGuideClickNext() {
        showGuideClick.value = false
        showGuideLongPress.value = true
    }

    fun onGuideLongPressNext() {
        showGuideLongPress.value = false
        showGuideClickClearAll.value = true
    }

    fun onGuideFinished() {
        showGuideClickClearAll.value = false
        with(prefs.edit()) {
            putBoolean("is_first_time_on_user_page", false)
            apply()
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            repository.updateUser(user)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            repository.deleteUser(user)
        }
    }

    fun resetDatabase() {
        viewModelScope.launch {
            repository.resetDatabase()
        }
    }

    private fun resetFields() {
        firstName.value = ""
        lastName.value = ""
        eMail.value = ""
    }
}

class UserViewModelFactory(private val repository: UserRepository, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}