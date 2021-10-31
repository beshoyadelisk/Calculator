package com.beshoy.thirdwayvcalculator.domain.command

import com.beshoy.thirdwayvcalculator.domain.service.Calculator
import com.beshoy.thirdwayvcalculator.domain.service.ExecutionServiceImpl
import org.junit.Assert
import org.junit.Test

class ClearCommandTest {
    private val calculator = Calculator()
    private val executionService = ExecutionServiceImpl()
    @Test
    fun execute() {
        executionService.execute(AddCommand(calculator, 2))
        executionService.execute(AddCommand(calculator, 3))
        executionService.execute(ClearCommand(executionService,calculator))
        Assert.assertEquals(0, calculator.currentValue)
    }
}