package com.example.testlogin.adapterDelegate

import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import com.example.domain.models.payments.Payment
import com.example.testlogin.R
import com.example.testlogin.databinding.ItemBinding
import ru.sr.adapter.ListDelegateAdapter
import ru.sr.adapter.adapterDelegate

class AdapterDelegate : ListDelegateAdapter<Payment>(
    PaymentDiffUtil()
) {
    init {
        addDelegate(
            payment()
        )
    }
}

fun payment() =
    adapterDelegate<Payment, Payment, ItemBinding>(
        { parent ->
            ItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        }
    ) {
        bind {
            binding.title.text = binding.root.context.getString(R.string.title, item.title)

            if (item.amount == null) {
                binding.amount.text = binding.root.context.getText(R.string.no_data)
            } else {
                when (item.amount) {
                    is String -> {
                        val amount = item.amount as String
                        if (amount.isEmpty()) {
                            binding.amount.text = binding.root.context.getText(R.string.no_data)
                        } else {
                            binding.amount.text = binding.root.context.getString(R.string.amount, item.amount)
                        }
                    }

                    is Double -> {
                        val amount = item.amount as Double
                        binding.amount.text = binding.root.context.getString(R.string.amount, amount.toString())
                    }

                    is Int -> {
                        val amount = item.amount as Int
                        binding.amount.text = binding.root.context.getString(R.string.amount, amount.toString())
                    }
                }
            }
            if (item.created == null) {
                binding.created.text = binding.root.context.getText(R.string.no_data2)
            } else {
                binding.created.text = binding.root.context.getString(R.string.code, item.created.toString())
            }
        }
    }

class PaymentDiffUtil : DiffUtil.ItemCallback<Payment>() {
    override fun areItemsTheSame(
        oldItem: Payment,
        newItem: Payment
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Payment,
        newItem: Payment
    ): Boolean = oldItem == newItem
}