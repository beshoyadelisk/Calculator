package com.beshoy.thirdwayvcalculator.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import com.beshoy.thirdwayvcalculator.databinding.ActivityMainBinding
import com.beshoy.thirdwayvcalculator.domain.model.OperationItem
import com.beshoy.thirdwayvcalculator.domain.model.OperationsEnum
import com.beshoy.thirdwayvcalculator.ui.adapter.OperationAdapter
import com.beshoy.thirdwayvcalculator.ui.listener.OperationListener
import com.beshoy.thirdwayvcalculator.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), OperationListener {
    private var mBinding: ActivityMainBinding? = null
    private val binding by lazy {
        mBinding!!
    }
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var adapter: OperationAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObservers()
        initAdapter()
        iniRecycler()
        binding.btnAdd.setOnClickListener { viewModel.setSelectedOperation(OperationsEnum.SUM) }
        binding.btnSub.setOnClickListener { viewModel.setSelectedOperation(OperationsEnum.SUB) }
        binding.btnMulti.setOnClickListener { viewModel.setSelectedOperation(OperationsEnum.MULTI) }
        binding.btnDivide.setOnClickListener { viewModel.setSelectedOperation(OperationsEnum.DIVIDE) }
        binding.btnEqual.setOnClickListener {
            viewModel.calculateResult(binding.etOperand.text.toString().toInt())
        }
        binding.etOperand.doOnTextChanged { _, _, _, _ ->
            binding.btnEqual.isEnabled = binding.etOperand.text.isNotEmpty()
        }
        binding.btnRedo.setOnClickListener { viewModel.redoAction() }
        binding.btnUndo.setOnClickListener { viewModel.undoAction() }
    }

    private fun initAdapter() {
        adapter = OperationAdapter(this)
    }

    private fun iniRecycler() {
        binding.recyclerView.layoutManager =
            GridLayoutManager(this, 4)
        binding.recyclerView.adapter = adapter
    }

    private fun initObservers() {
        viewModel.selectedOperation.observe(this) { operationObserver(it) }
        viewModel.lastValue.observe(this) { lastValueObserver(it) }
        viewModel.operationItem.observe(this) { operationItemObserver(it) }
        viewModel.isUndoable.observe(this) { undoableObserver(it) }
        viewModel.isRedoable.observe(this) { redoableObserver(it) }
    }

    private fun redoableObserver(isRedoable: Boolean) {
        binding.btnRedo.isEnabled = isRedoable
    }

    private fun undoableObserver(isUndoable: Boolean) {
        binding.btnUndo.isEnabled = isUndoable
    }

    private fun operationItemObserver(operationItem: OperationItem) {
        if (this::adapter.isInitialized) {
            adapter.addOperation(operationItem)
        }
    }

    private fun lastValueObserver(result: Int) {
        val resultString = "Result = $result"
        binding.textView.text = resultString
        clearSelection()
    }

    private fun operationObserver(it: OperationsEnum) {
        if (it == OperationsEnum.NOT_SPECIFIED) binding.toggleGroup.clearChecked()
        else {
            binding.etOperand.isEnabled = true
            binding.etOperand.requestFocus()
        }
    }

    private fun clearSelection() {
        viewModel.removeSelections()
        binding.etOperand.text.clear()
        binding.etOperand.isEnabled = false
    }

    override fun onItemClicked(position: Int) {
        viewModel.undoAction(position)
    }
}