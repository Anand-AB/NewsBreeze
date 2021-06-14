package com.anand.newsbreeze.data

import android.util.Log
import com.anand.newsbreeze.domain.exceptions.MyException
import com.google.gson.JsonSyntaxException
import com.google.gson.stream.MalformedJsonException
import kotlinx.coroutines.Deferred
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseRepository {

    suspend fun <R> either(service: Deferred<R>): Either<MyException, R> {
        return try {
            Log.d("LOG_PATIENT", "BASEREPO_19: ${service}")
            Either.Right(service.await())
        } catch (e: Exception) {
            if (e is HttpException) {
                Log.d("LOG_PATIENT", "BASEREPO_21: $e")
                if (e.code() == 422) {
                    Log.d("LOG_PATIENT", "BASEREPO_23: $e")
                    Either.Left(transformException(e))
                    //Either.Right(service.await())
                } else {
                    Log.d("LOG_PATIENT", "BASEREPO_20: $e")
                    e.printStackTrace()
                    Either.Left(transformException(e))
                }
            } else {
                Log.d("LOG_PATIENT", "BASEREPO_30: $e")
                e.printStackTrace()
                Either.Left(transformException(e))
            }
        }
    }

    suspend fun <R> eitherLocal(service: R): Either<MyException, R> {
        return try {
            Either.Right(service)
        } catch (e: Exception) {
            e.printStackTrace()
            Either.Left(transformException(e))
        }
    }

    private fun transformException(e: Exception): MyException {
        Log.d("LOG_PATIENT", "BASEREPO_37: " + e)
        if (e is HttpException) {
            when (e.code()) {
                422 -> return MyException.JsonException("Unable to parse api response", e)
                502 -> return MyException.JsonException("Unable to parse api response", e)
                500 -> return MyException.JsonException(
                    "Something went wrong",
                    e
                )// internal server error
                //500 -> return MyException.InternetConnectionException("Something went wrong", e)// internal server error
            }
        } else {
            if (e is ConnectException || e is UnknownHostException || e is SocketTimeoutException || e is SocketException) {
                return MyException.InternetConnectionException("No internet available", e)
            } else if (e is IllegalStateException || e is JsonSyntaxException || e is MalformedJsonException) {
                return MyException.JsonException("Unable to parse api response", e)
            }
        }
        return MyException.UnknownException(e)
    }

}