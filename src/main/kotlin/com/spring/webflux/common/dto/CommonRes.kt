package com.spring.webflux.common.dto

import kotlinx.serialization.Serializable
import org.springframework.http.HttpStatus

@Serializable
data class CommonRes<T>(
    val code:Int,
    val message:String,
    val data:T? = null
){
    constructor(httpStatus: HttpStatus, data:T?):this(httpStatus.value(), httpStatus.name, data)

    companion object {
        fun success(): CommonRes<Any> = CommonRes(200, "OK")
        fun success(data: Any): CommonRes<Any> = CommonRes(200, "OK", data)

        fun error(code: Int, message: String): CommonRes<Any> = CommonRes(code, message, null)

        /*     fun validObjectErrors(errors: List<ObjectError>): ResponseEntity<Any> {
                 val errorsList = errors.map {
                     ValidDTO(it.objectName, it.defaultMessage)
                 }

                 return this.valid(errorsList)
             }
             fun valid(errors: List<ValidDTO>): ResponseEntity<Any> {
                 val validList = arrayListOf<ValidDTO>()

                 errors.forEach{ error ->

                     val valid = ValidDTO(error.field, error.message)

                     validList.add(valid)
                 }

                 return ResponseEntity.ok(CommonRes(HttpStatus.BAD_REQUEST, validList))
             }*/
    }
}