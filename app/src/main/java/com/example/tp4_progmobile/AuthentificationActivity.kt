package com.example.tp4_progmobile

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AuthentificationActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var ed_email: EditText
    private lateinit var ed_mdp: EditText
    private lateinit var btn_login: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        ed_email = findViewById(R.id.ed_email)
        ed_mdp = findViewById(R.id.ed_mdp)

        btn_login = findViewById(R.id.btn_login)

        btn_login.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
        when(v?.id){
            R.id.btn_login -> {
                TODO("Not yet implemented")
            }
        }
    }
}