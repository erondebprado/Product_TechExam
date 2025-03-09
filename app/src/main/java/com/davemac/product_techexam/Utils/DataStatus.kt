package com.davemac.product_techexam.Utils

class DataStatus<out T>(val status: Status, val data: T?= null, val message: String?= null) {

    enum class Status {
        LOADING, SUCCESS, ERROR
    }
    companion object{

        fun <T> loading(): DataStatus<T>{
            return DataStatus(Status.LOADING)
        }

        fun <T> success(data: T?): DataStatus<T>{
            return DataStatus(Status.SUCCESS, data)
        }

        fun <T> error(msg: String): DataStatus<T>{
            return DataStatus(Status.ERROR, message = msg)
        }
    }
}