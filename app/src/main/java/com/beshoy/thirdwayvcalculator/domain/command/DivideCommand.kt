package com.beshoy.thirdwayvcalculator.domain.command

import com.beshoy.thirdwayvcalculator.domain.command.interfaces.UndoableCommand
import com.beshoy.thirdwayvcalculator.domain.service.Calculator
import java.lang.Exception

class DivideCommand(
    private val calculator: Calculator,
    private val value: Int
) : UndoableCommand {
    override fun undo() {
        calculator.multiply(value)
    }

    override fun execute(): Boolean {
        return try {
            calculator.divide(value)
            true
        } catch (ex: Exception) {
            false
        }

    }
}