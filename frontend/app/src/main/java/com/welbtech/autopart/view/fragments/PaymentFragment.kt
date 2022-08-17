package com.welbtech.autopart.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.welbtech.autopart.view.util.navById
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.welbtech.autopart.R
import com.welbtech.autopart.databinding.FragmentPaymentBinding


class PaymentFragment : Fragment() {
    lateinit var binding: FragmentPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPaymentBinding.inflate(layoutInflater)
        binding.checkOutPage.setOnClickListener {
            //  onRadioButtonClicked(it)
            val dialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.card_details, null)

            val payBtn = view.findViewById<Button>(R.id.payment)
            payBtn.setOnClickListener {
                navById(R.id.action_paymentFragment_to_orderFragment)
                dialog.dismiss()
            }

            dialog.setCancelable(true)
            dialog.setContentView(view)
            dialog.show()
        }
        return binding.root
    }

    fun onRadioButtonClicked(view: View) {
        val dialog = BottomSheetDialog(requireContext())
        val viewLayoutInflater = layoutInflater.inflate(R.layout.card_details, null)
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.credit_card ->
                    if (checked) {
                        dialog.setCancelable(true)
                        dialog.setContentView(viewLayoutInflater)
                        dialog.show()
                    }
                R.id.paypal ->
                    if (checked) {
                        // Ninjas rule
                    }

                R.id.gpay ->
                    if (checked) {
                        // Ninjas rule
                    }
            }
        }
    }

}