package com.example.myapplication

import android.app.Application
import com.example.myapplication.util.UserDatabase
import com.example.myapplication.util.UserRepository

class MyApplication : Application() {
    private val database by lazy { UserDatabase.getDatabase(this) }
    val repository by lazy { UserRepository(database.userDao()) }
}