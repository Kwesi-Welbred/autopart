package com.android.automobile.data.source

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.android.automobile.data.source.UserPreferences.Companion.DataStore_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


//Extension  variable for storing the credentials
private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = DataStore_NAME)
class UserPreferences(private val context: Context) : Abstract {
    //saving the credentials
    override suspend fun saveCredentials(credential: Credentials) {
        context.datastore.edit { credentials ->
            credentials[EMAIL] = credential.email
            credentials[PASSWORD] = credential.password
        }
    }

    //getting the credentials
    override suspend fun getCredentials()= context.datastore.data.map{credentials->
        Credentials(
            email = credentials[EMAIL]?:"Empty email",
            password = credentials[PASSWORD]?:"Empty password"
        )
    }

    override suspend fun saveAccessTokens(tokens: Tokens) {
        context.datastore.edit { preferences ->
            preferences[ACCESS_TOKEN] = tokens.accessToken
            preferences[REFRESH_TOKEN] = tokens.refreshToken
        }
    }

    override suspend fun getAccessToken()=context.datastore.data.map { tokens->
            tokens[ACCESS_TOKEN]?:""
    }

    override suspend fun getRefreshToken()=context.datastore.data.map { tokens->
            tokens[REFRESH_TOKEN]?:""
    }

    override suspend fun clearCredentials() {
        context.datastore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        const val DataStore_NAME = "CREDENTIALS"
        val EMAIL = stringPreferencesKey("EMAIL")
        val PASSWORD = stringPreferencesKey("PASSWORD")
        private val ACCESS_TOKEN = stringPreferencesKey("key_access_token")
        private val REFRESH_TOKEN = stringPreferencesKey("key_refresh_token")
    }
}


interface Abstract {
    suspend fun saveCredentials(credential: Credentials)
    suspend fun getCredentials(): Flow<Credentials>
    suspend fun saveAccessTokens(tokens: Tokens)
    suspend fun getAccessToken():Flow<String>
    suspend fun getRefreshToken():Flow<String?>
    suspend fun clearCredentials()
}

data class Credentials(
    val email: String,
    val password: String
)

data class Tokens(
    val accessToken: String,
    val refreshToken: String
)