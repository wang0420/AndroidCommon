package com.module.kt.presenter

class UserPresenter(val repo: UserRepo) {

    fun sayHi() = "Hi,${repo.getName()}"
}