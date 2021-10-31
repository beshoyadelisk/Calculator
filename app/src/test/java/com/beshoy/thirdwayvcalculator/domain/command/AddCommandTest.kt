package com.beshoy.thirdwayvcalculator.domain.command

import com.beshoy.thirdwayvcalculator.domain.service.Calculator
import com.beshoy.thirdwayvcalculator.domain.service.ExecutionServiceImpl
import org.junit.Assert.assertEquals
import org.junit.Test

class AddCommandTest {
    private val calculator = Calculator()
    private val executionService = ExecutionServiceImpl()


    @Test
    fun undo() {
        executionService.execute(AddCommand(calculator, 2))
        executionService.execute(AddCommand(calculator, 3))
        executionService.undo()
        assertEquals(2,calculator.currentValue)
    }

    @Test
    fun execute() {
        executionService.execute(AddCommand(calculator, 2))
        executionService.execute(AddCommand(calculator, 3))
        assertEquals(5, calculator.currentValue)
    }

    @Test
    fun redo(){
        executionService.execute(AddCommand(calculator, 2))
        executionService.execute(AddCommand(calculator, 3))
        executionService.undo()
        executionService.redo()
        assertEquals(5,calculator.currentValue)
    }
}