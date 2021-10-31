package com.beshoy.thirdwayvcalculator.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beshoy.thirdwayvcalculator.databinding.OperationCellBinding
import com.beshoy.thirdwayvcalculator.domain.model.OperationItem
import com.beshoy.thirdwayvcalculator.domain.model.OperationsEnum
import com.beshoy.thirdwayvcalculator.ui.listener.OperationListener

class OperationAdapter(private var operationListener: OperationListener) :
    RecyclerView.Adapter<OperationAdapter.ViewHolder>() {
    private var operationList: MutableList<OperationItem> = mutableListOf()

    fun addOperation(operationItem: OperationItem) {
        this.operationList.add(0, operationItem)
        notifyItemInserted(0)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            OperationCellBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun getItemCount(): Int =
        operationList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(operationList[position], operationListener)
    }

    inner class ViewHolder(var binding: OperationCellBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {
        private lateinit var operationItem: OperationItem
        private lateinit var operationListener: OperationListener

        init {
            binding.root.setOnClickListener(this)
        }

        fun setData(operationItem: OperationItem, operationListener: OperationListener) {
            this.operationItem = operationItem
            val operationText = when (operationItem.operation) {
                OperationsEnum.SUM -> "+"
                OperationsEnum.SUB -> "-"
                OperationsEnum.DIVIDE -> "/"
                OperationsEnum.MULTI -> "*"
                OperationsEnum.NOT_SPECIFIED -> null
            }
            operationText?.let {
                val text = it + " " + operationItem.operand
                binding.etOperation.text = text
            }
            this.operationListener = operationListener
        }

        override fun onClick(v: View?) {
            if (adapterPosition >= 0) {
                operationListener.onItemClicked(adapterPosition)
            }
        }
    }
}