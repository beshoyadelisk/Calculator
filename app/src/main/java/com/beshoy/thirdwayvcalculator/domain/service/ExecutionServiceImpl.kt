package com.beshoy.thirdwayvcalculator.domain.service

import com.beshoy.thirdwayvcalculator.domain.command.interfaces.Command
import com.beshoy.thirdwayvcalculator.domain.command.interfaces.UndoableCommand
import com.beshoy.thirdwayvcalculator.domain.service.interfaces.ExecutionService
import java.util.*


class ExecutionServiceImpl : ExecutionService {
    private val undoStack = Stack<UndoableCommand?>()
    private val redoStack = Stack<UndoableCommand>()

    override fun execute(command: Command): Boolean {
        if (command is UndoableCommand) {
            undoStack.push(command as UndoableCommand?)
        }
        return if (command.execute()) {
            redoStack.clear()
            true
        } else {
            false
        }
    }

    override fun undo(): Boolean {
        if (undoStack.isEmpty()) {
            return false
        }
        return undoCommand(undoStack.pop())
    }

    override fun redo(): Boolean {
        if (redoStack.isEmpty()) {
            return false
        }
        val command = redoStack.pop()
        command?.let {
            undoStack.push(it)
            it.execute()
            return true
        } ?: return false

    }


    private fun undoCommand(command: UndoableCommand?): Boolean {
        command?.let {
            redoStack.push(it)
            it.undo()
            return true
        } ?: return false
    }


    override fun hasUndoableCommand(): Boolean {
        return undoStack.isNotEmpty()
    }

    override fun hasRedoAbleCommand(): Boolean {
        return redoStack.isNotEmpty()
    }

    override fun clear() {
        undoStack.clear()
        redoStack.clear()
    }


}