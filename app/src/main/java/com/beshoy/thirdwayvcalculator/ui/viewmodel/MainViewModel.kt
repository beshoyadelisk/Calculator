package com.beshoy.thirdwayvcalculator.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beshoy.thirdwayvcalculator.domain.command.AddCommand
import com.beshoy.thirdwayvcalculator.domain.command.DivideCommand
import com.beshoy.thirdwayvcalculator.domain.command.MultiplyCommand
import com.beshoy.thirdwayvcalculator.domain.command.SubtractCommand
import com.beshoy.thirdwayvcalculator.domain.model.OperationItem
import com.beshoy.thirdwayvcalculator.domain.model.OperationsEnum
import com.beshoy.thirdwayvcalculator.domain.service.Calculator
import com.beshoy.thirdwayvcalculator.domain.service.ExecutionServiceImpl


class MainViewModel : ViewModel() {
    private val executionService = ExecutionServiceImpl()
    private val calculator = Calculator()

    private val mSelectedOperation = MutableLiveData<OperationsEnum>()
    val selectedOperation: LiveData<OperationsEnum> get() = mSelectedOperation

    private val mOperationItem = MutableLiveData<OperationItem>()
    val operationItem: LiveData<OperationItem> get() = mOperationItem

    private val mLastValue = MutableLiveData(calculator.currentValue)
    val lastValue: LiveData<Int> get() = mLastValue

    private val mIsUndoable = MutableLiveData(false)
    val isUndoable: LiveData<Boolean> get() = mIsUndoable

    private val mIsRedoable = MutableLiveData(false)
    val isRedoable: LiveData<Boolean> get() = mIsRedoable

    fun setSelectedOperation(operation: OperationsEnum) {
        mSelectedOperation.value = operation
    }

    fun removeSelections() {
        mSelectedOperation.value = OperationsEnum.NOT_SPECIFIED
    }

    fun calculateResult(operand: Int) {
        val result = when (mSelectedOperation.value) {
            OperationsEnum.SUM -> {
                executionService.execute(AddCommand(calculator, operand))
            }
            OperationsEnum.SUB -> {
                executionService.execute(SubtractCommand(calculator, operand))
            }
            OperationsEnum.DIVIDE -> {
                executionService.execute(DivideCommand(calculator, operand))
            }
            OperationsEnum.MULTI -> {
                executionService.execute(MultiplyCommand(calculator, operand))
            }
            else -> false
        }
        if (result) {
            mOperationItem.value =
                OperationItem(operand, mSelectedOperation.value!!, calculator.currentValue)
            mLastValue.value = calculator.currentValue
            checkRedoUndoActions()
        }
    }

    fun redoAction() {
        if (executionService.hasRedoAbleCommand()) {
            executionService.redo()
            mLastValue.value = calculator.currentValue
            checkRedoUndoActions()
        }
    }

    fun undoAction() {
        if (executionService.hasUndoableCommand()) {
            executionService.undo()
            mLastValue.value = calculator.currentValue
            checkRedoUndoActions()
        }
    }

    fun undoAction(position: Int) {
        if (executionService.hasUndoableCommandAt(position)) {
            executionService.undoAt(position)
            mLastValue.value = calculator.currentValue
            checkRedoUndoActions()
        }
    }

    private fun checkRedoUndoActions() {
        mIsRedoable.value = executionService.hasRedoAbleCommand()
        mIsUndoable.value = executionService.hasUndoableCommand()
    }


}