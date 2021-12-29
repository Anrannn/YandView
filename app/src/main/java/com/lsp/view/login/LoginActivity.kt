package com.lsp.view.login

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lsp.view.R
import com.lsp.view.main.MainActivity
import okhttp3.*
import java.io.File
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.ArrayList
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val login = findViewById<FloatingActionButton>(R.id.login)


        login.setOnClickListener {
            val username = findViewById<EditText>(R.id.username)
            val password = findViewById<EditText>(R.id.password)
            val sPassword = "choujin-steiner--${password.text}--"
            val hash1Password = StrToHash1.shaEncrypt(sPassword)
            thread {
                conn(username.text.toString(),hash1Password)
            }
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)

        }



    }
    fun conn(username:String,hash1: String):String{
        val cookies = ArrayList<Cookie>()
        val client = OkHttpClient.Builder()
            .connectTimeout(60,TimeUnit.SECONDS)
            .cookieJar(object : CookieJar{
                override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
                }

                override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
                    return cookies
                }

            })
            .build()
        val request = Request.Builder()
            .url("https://yande.re/?username=$username&password=$hash1")
            .build()
        return client.newCall(request).execute().body()!!.string()
    }

}