package br.org.cesar.discordtime.stickysessions.data.local.repository

import br.org.cesar.discordtime.stickysessions.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Single

class UserLocalRepository : UserRepository {

    override fun getSavedUser(): Single<String> {
        return Single.create { emitter ->
            val auth = FirebaseAuth.getInstance()
            val user = auth.currentUser
            if (user != null) {
                emitter.onSuccess(user.displayName ?: user.email ?: user.uid)
            } else {
                emitter.onError(Exception("User Not found"))
            }
        }
    }
}
