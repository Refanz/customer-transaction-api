package com.refanzzzz.apicustomertransaction.util

import org.springframework.data.domain.Sort

object SortUtil {
    fun parseSort(sort: String): Sort {
        var sortBy = Sort.unsorted()

        sortBy = if (sort.startsWith("-")) Sort.by(
            Sort.Direction.DESC,
            sort.substring(1)
        ) else Sort.by(Sort.Direction.ASC, sort)

        return sortBy
    }
}