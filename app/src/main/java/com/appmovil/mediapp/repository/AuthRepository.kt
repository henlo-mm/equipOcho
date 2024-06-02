package com.appmovil.mediapp.repository
import com.google.firebase.auth.FirebaseAuth

class AuthRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()

    fun registerUser(email: String, password:String, isRegisterComplete: (Boolean)->Unit){
        if(email.isNotEmpty() && password.isNotEmpty()){
            firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        isRegisterComplete(true)
                    } else {
                        isRegisterComplete(false)
                    }
                }
        }else{
            isRegisterComplete(false)
        }
    }


    fun loginUser(email: String, password: String, isLogin: (Boolean) -> Unit) {

        if (email.isNotEmpty() && password.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        isLogin(true)
                    } else {
                        isLogin(false)
                    }
                }
        } else {
            isLogin(false)
        }
    }

    fun sesion(email: String?, isEnableView: (Boolean) -> Unit) {
        if (email != null) {
            isEnableView(true)
        } else {
            isEnableView(false)
        }
    }

}