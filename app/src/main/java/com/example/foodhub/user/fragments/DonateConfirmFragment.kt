package com.example.foodhub.user.fragments

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodhub.R
import com.example.foodhub.admin.viewmodels.DonationViewModel
import com.example.foodhub.database.tables.Donation
import com.example.foodhub.databinding.FragmentDonateBinding
import com.example.foodhub.databinding.FragmentDonateConfirmBinding
import com.example.foodhub.login.UserViewModel
import com.example.foodhub.user.viewmodels.DonateViewModal
import java.util.Calendar

class DonateConfirmFragment : Fragment() {
    private lateinit var mUserViewModel: UserViewModel
    private lateinit var bindingDonateConfirm: FragmentDonateConfirmBinding
    private val viewModel: DonateViewModal by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        bindingDonateConfirm = DataBindingUtil.inflate(inflater, R.layout.fragment_donate_confirm, container, false)
        return bindingDonateConfirm.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = ""
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.sign_out_circle)



        var number = viewModel.donateAmount.value
        bindingDonateConfirm.donationAmount.text = "RM $number"

        var paymentMethodName = viewModel.donateMethod.value?:"error"
        val paymentMethodNames = arrayOf("Visa","MasterCard","American Express","Union Pay")
        val paymentMethodImage = arrayOf(R.drawable.visa,R.drawable.mastercard_2,R.drawable.american_express_1,R.drawable.unionpay_seeklogo_com)
        bindingDonateConfirm.donatePaymentImage.setImageResource(paymentMethodImage[paymentMethodImageNum(paymentMethodName)])
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        bindingDonateConfirm.confirmDonation.setOnClickListener{
            if (bindingDonateConfirm.cardNumber.text.isEmpty() || bindingDonateConfirm.cardNumber.text.length < 15){
                if(bindingDonateConfirm.cardNumber.text.isEmpty()){
                    Toast.makeText(requireContext(), "Please enter card number", Toast.LENGTH_SHORT).show()
                    bindingDonateConfirm.cardNumber.setBackgroundColor(Color.parseColor("#FFFF3030"))
                    bindingDonateConfirm.cardNumber.setBackgroundColor(Color.parseColor("#FFFF3030"))
                }else if(bindingDonateConfirm.cardNumber.text.length < 15){
                    Toast.makeText(requireContext(), "Invalid card number", Toast.LENGTH_SHORT).show()
                }
            }else if(bindingDonateConfirm.monthExpiry.text.isEmpty() || bindingDonateConfirm.monthExpiry.text.toString().toInt() > 12){
                bindingDonateConfirm.cardNumber.background = null
                if(bindingDonateConfirm.monthExpiry.text.isEmpty()){
                    Toast.makeText(requireContext(), "Please enter a month of expiration", Toast.LENGTH_SHORT).show()
                    bindingDonateConfirm.monthExpiry.setBackgroundColor(Color.parseColor("#FFFF3030"))
                }else if(bindingDonateConfirm.monthExpiry.text.toString().toInt() > 12){
                    Toast.makeText(requireContext(), "Invalid month of expiration", Toast.LENGTH_SHORT).show()
                    bindingDonateConfirm.monthExpiry.setBackgroundColor(Color.parseColor("#FFFF3030"))
                }
            }else if(bindingDonateConfirm.yearExpiry.text.isEmpty() || bindingDonateConfirm.yearExpiry.text.toString().toInt() < year){
                bindingDonateConfirm.cardNumber.background = null
                bindingDonateConfirm.monthExpiry.background = null
                if(bindingDonateConfirm.yearExpiry.text.isEmpty()){
                    Toast.makeText(requireContext(), "Please enter a year of expiration", Toast.LENGTH_SHORT).show()
                    bindingDonateConfirm.yearExpiry.setBackgroundColor(Color.parseColor("#FFFF3030"))
                }else if(bindingDonateConfirm.yearExpiry.text.toString().toInt() < year){
                    Toast.makeText(requireContext(), "Invalid year of expiration", Toast.LENGTH_SHORT).show()
                    bindingDonateConfirm.yearExpiry.setBackgroundColor(Color.parseColor("#FFFF3030"))
                }
            }else if(bindingDonateConfirm.cvv.text.isEmpty()){
                bindingDonateConfirm.yearExpiry.background = null
                bindingDonateConfirm.cardNumber.background = null
                bindingDonateConfirm.monthExpiry.background = null
                Toast.makeText(requireContext(), "Please enter your cvv", Toast.LENGTH_SHORT).show()
                bindingDonateConfirm.cvv.setBackgroundColor(Color.parseColor("#FFFF3030"))
            }else if(!bindingDonateConfirm.checkBox.isChecked){
                bindingDonateConfirm.yearExpiry.background = null
                bindingDonateConfirm.cardNumber.background = null
                bindingDonateConfirm.monthExpiry.background = null
                bindingDonateConfirm.cvv.background = null
                Toast.makeText(requireContext(), "Please check the terms and condition box", Toast.LENGTH_SHORT).show()
            }else{

                view.findNavController().navigate(R.id.donateSuccessFragment)

            }
        }
        bindingDonateConfirm.termsAndCondition.setOnClickListener{
            var user = mUserViewModel.getUser(viewModel.userID.value?:"")
            if (number != null) {
                if (user != null) {
                    mUserViewModel.addDonation(Donation(
                        dId = 0,
                        uId = user.loginID,
                        dMethod=paymentMethodNames[paymentMethodImageNum(paymentMethodName)],
                        dAmount = number.toDouble()))
                }
            }
            view.findNavController().navigate(R.id.termsAndConditionFragment)
        }

    }
    override fun onResume() {
        super.onResume()
        viewModel.tnc.observe(viewLifecycleOwner) { isChecked ->
            bindingDonateConfirm.checkBox.isChecked = isChecked
        }
    }


}


fun paymentMethodImageNum(paymentMethodName:String) : Int{
    if (paymentMethodName == "Visa"){
        return 0
    }else if (paymentMethodName == "MasterCard"){
        return 1
    }else if (paymentMethodName == "American Express"){
        return 2
    }else if (paymentMethodName == "Union Pay") {
        return 3
    }else{
        return 4
    }


}








