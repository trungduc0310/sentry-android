package com.tcom.logingsdk

import io.sentry.Sentry
import io.sentry.protocol.User

public class UserBuilder {
    private var email: String = ""
    private var userName: String = ""
    private var id: String = ""
    private var ipAddress: String = ""
    private var others: Map<String, String>? = HashMap<String, String>()
    private var user = User()

    fun setEmail(email: String): UserBuilder {
        this.email = email
        return this
    }

    fun setUserName(userName: String): UserBuilder {
        this.userName = userName
        return this
    }

    fun setId(id: String): UserBuilder {
        this.id = id
        return this
    }

    fun setIpAddress(ipAddress: String): UserBuilder {
        this.ipAddress = ipAddress
        return this
    }

    fun setOthers(others: Map<String, String>): UserBuilder {
        this.others = others
        return this
    }

    fun create() {
        this.user.email = if (this.email.isNotEmpty()) this.email else ""
        this.user.id = if (this.id.isNotEmpty()) this.id else ""
        this.user.ipAddress = if (this.ipAddress.isNotEmpty()) this.ipAddress else ""
        this.user.others = this.others
        this.user.username = if (this.userName.isNotEmpty()) this.userName else ""
        Sentry.configureScope { scope -> scope.user = this.user }
    }

    fun getUser(): User {
        return this.user
    }

    fun unSetUser() {
        Sentry.configureScope { scope -> scope.user = null }
    }

}