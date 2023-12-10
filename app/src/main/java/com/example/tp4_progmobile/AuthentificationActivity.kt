package com.example.tp4_progmobile

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.tp4_progmobile.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser

/**
 * Activité d'authentification
 * Gère la connexion de l'utilisateur à Firebase
 */
class AuthentificationActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var binding: ActivityLoginBinding
    private var mAuth: FirebaseAuth? = null

    private lateinit var ed_email: EditText
    private lateinit var ed_mdp: EditText
    private lateinit var btn_login: Button
    private lateinit var btn_signup: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        ed_email = binding.edEmail
        ed_mdp = binding.edMdp

        btn_login = binding.btnLogin
        btn_signup = binding.btnSignup

        btn_login.setOnClickListener(this)
        btn_signup.setOnClickListener(this)

    }

    /**
     * Gestion des cliques sur les bouton Login et Signup
     */
    override fun onClick(v: View?) {
        when(v?.id){
            binding.btnLogin.id -> {
                if (mAuth!!.currentUser != null){
                    Toast.makeText(this, "Vous êtes déjà connecté.", Toast.LENGTH_SHORT).show()
                    startActivityMain()
                }else{
                    val email = ed_email.text.toString()
                    val password = ed_mdp.text.toString()
                    if (email.isNotEmpty() && password.isNotEmpty()){
                        mAuth!!.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this) {task ->
                                if(task.isSuccessful){
                                    val user = mAuth!!.currentUser
                                    Toast.makeText(this, "Connexion réussie.", Toast.LENGTH_SHORT).show()
                                    startActivityMain()
                                }else{
                                    if (task.exception is FirebaseAuthInvalidUserException) {
                                        Toast.makeText(this, "Compte inexistant.", Toast.LENGTH_SHORT).show()
                                        ed_mdp.text.clear()
                                    } else if (task.exception is FirebaseAuthInvalidCredentialsException) {
                                        Toast.makeText(this, "Mot de passe incorrect.", Toast.LENGTH_SHORT).show()
                                        ed_mdp.text.clear()
                                    } else {
                                        Toast.makeText(this, "Erreur de connexion.", Toast.LENGTH_SHORT).show()
                                    }
                                    ed_email.backgroundTintList = ColorStateList.valueOf(
                                        ContextCompat.getColor(this, android.R.color.holo_red_light)
                                    )
                                }
                            }
                    }else{
                        Toast.makeText(this, "Veuillez saisir une adresse e-mail et un mot de passe.", Toast.LENGTH_SHORT).show()
                        ed_email.backgroundTintList = ColorStateList.valueOf(
                            ContextCompat.getColor(this, android.R.color.holo_red_light)
                        )
                    }

                }
            }
            binding.btnSignup.id -> {
                val email = binding.edEmail.text.toString()
                val password = binding.edMdp.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        if (password.length >= 6) {
                            mAuth!!.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(this) { task ->
                                    if (task.isSuccessful) {
                                        val user = mAuth!!.currentUser
                                        Toast.makeText(this, "Compte créé avec succès.", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(this, "Échec de la création du compte.", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        } else {
                            Toast.makeText(this, "Le mot de passe doit contenir au moins 6 caractères.", Toast.LENGTH_SHORT).show()
                            ed_mdp.backgroundTintList = ColorStateList.valueOf(
                                ContextCompat.getColor(this, android.R.color.holo_red_light)
                            )
                        }
                    } else {
                        Toast.makeText(this, "Format d'adresse e-mail incorrect.", Toast.LENGTH_SHORT).show()
                        ed_email.backgroundTintList = ColorStateList.valueOf(
                            ContextCompat.getColor(this, android.R.color.holo_red_light)
                        )
                    }
                } else {
                    Toast.makeText(this, "Veuillez saisir une adresse e-mail et un mot de passe.", Toast.LENGTH_SHORT).show()
                    ed_email.backgroundTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(this, android.R.color.holo_red_light)
                    )
                }
            }
        }
    }

    /**
     * Lance l'activité principale
     */
    private fun startActivityMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}