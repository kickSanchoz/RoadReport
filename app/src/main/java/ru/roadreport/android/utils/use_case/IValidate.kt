package ru.roadreport.android.utils.use_case

interface IValidate <T> {
    fun execute(data: T?): ValidationResult
}