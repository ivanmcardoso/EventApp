package com.sicredtest.eventapp.utils

import android.util.Patterns

fun CharSequence?.isValid() = !isNullOrEmpty() && !isNullOrBlank()
fun CharSequence?.isEmailValid() = isValid() && Patterns.EMAIL_ADDRESS.matcher(this).matches()