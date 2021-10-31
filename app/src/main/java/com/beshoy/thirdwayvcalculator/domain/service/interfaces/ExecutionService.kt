package com.beshoy.thirdwayvcalculator.domain.service.interfaces

import com.beshoy.thirdwayvcalculator.domain.command.interfaces.Command


interface ExecutionService {
    fun execute(command: Command): Boolean

    fun undo(): Boolean

    fun redo(): Boolean

    fun hasUndoableCommand(): Boolean

    fun hasRedoAbleCommand(): Boolean

    fun clear()
}