package br.org.cesar.discordtime.stickysessions.auth.firebase

import br.org.cesar.discordtime.stickysessions.auth.TokenProvider
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult

class FirebaseTokenProvider : TokenProvider {
    override fun getToken(): String {
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        return user?.run {
            val task: Task<GetTokenResult> = user.getIdToken(false)
            val result: GetTokenResult = Tasks.await(task)
            result.token ?: ""
        } as String
    }
}