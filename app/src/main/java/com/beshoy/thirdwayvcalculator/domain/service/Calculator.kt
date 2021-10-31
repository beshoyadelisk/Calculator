package com.beshoy.thirdwayvcalculator.domain.service

class Calculator {
    var currentValue = 0

    fun add(operand: Int) {
        currentValue += operand
    }

    fun subtract(operand: Int) {
        currentValue -= operand
    }

    fun multiply(operand: Int) {
        currentValue *= operand
    }

    fun divide(operand: Int) {
        if (currentValue == 0)
            return
        currentValue /= operand
    }


}