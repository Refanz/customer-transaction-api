package com.refanzzzz.apicustomertransaction.util

import com.refanzzzz.apicustomertransaction.dto.response.CommonResponse
import com.refanzzzz.apicustomertransaction.dto.response.PagingResponse
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

object ResponseUtil {
    fun <T> buildResponse(httpStatus: HttpStatus, message: String, data: T): ResponseEntity<CommonResponse<T>> {
        val response = CommonResponse<T>(
            status = httpStatus.name,
            message = message,
            data = data
        )

        return ResponseEntity.status(httpStatus).body(response)
    }

    fun <T> buildResponseWithPaging(
        httpStatus: HttpStatus,
        message: String,
        data: T,
        page: Page<T>
    ): ResponseEntity<CommonResponse<T>> {
        val pagingResponse = PagingResponse(
            totalPages = page.totalPages,
            page = page.pageable.pageNumber,
            totalItems = page.totalElements,
            size = page.size
        )

        val response = CommonResponse<T>(
            status = httpStatus.name,
            message = message,
            data = data,
            paging = pagingResponse
        )

        return ResponseEntity.status(httpStatus).body(response)
    }
}