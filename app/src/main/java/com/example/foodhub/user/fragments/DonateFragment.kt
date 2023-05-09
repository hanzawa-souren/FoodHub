package com.example.foodhub.user.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodhub.DonateCardAdapter
import com.example.foodhub.R
import com.example.foodhub.databinding.FragmentDonateBinding
import com.example.foodhub.donateAmountAdapter
import com.example.foodhub.donateAmountModel
import com.example.foodhub.user.viewmodels.DonateCardModel
import com.example.foodhub.user.viewmodels.DonateViewModal


class DonateFragment : Fragment() {

    private lateinit var bindingDonate: FragmentDonateBinding
    private val viewModel: DonateViewModal by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_donate, container, false)

        bindingDonate = DataBindingUtil.inflate(inflater, R.layout.fragment_donate, container, false)
        return bindingDonate.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = ""

        (activity as AppCompatActivity).supportActionBar?.hide()
        val rview = bindingDonate.rview
        rview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        var methodCheck = 0
        rview.setHasFixedSize(true)
        var arrayList : ArrayList<DonateCardModel> = setDataList()
        var adapter1 = DonateCardAdapter(arrayList)
        rview.adapter = adapter1


        adapter1.onItemClick={

                donateCardModel ->
            Toast.makeText(requireContext(), "${viewModel.userID.value}", Toast.LENGTH_SHORT).show()
            methodCheck =1
            for (i in arrayList) {
                if (i.name != donateCardModel.name) {
                    i.status = 1f
                }else{
                    i.status = 0.6f
                    viewModel.donateMethod.value = i.name
                }
            }
            adapter1.notifyDataSetChanged()


        }
        val rview2 = bindingDonate.quickSelect
        rview2.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        rview.setHasFixedSize(true)
        val adapter2 = donateAmountAdapter(setDataListAmount())
        rview2.adapter = adapter2
        adapter2.onItemClick={
                donateAmountModel ->
            bindingDonate.amount.setText(donateAmountModel.amountNum)


        }
        bindingDonate.confirmDonate.setOnClickListener{
            if (methodCheck == 0){
                Toast.makeText(requireContext(), "Please select a payment method", Toast.LENGTH_SHORT).show()
            }else if(bindingDonate.amount.text.isEmpty()){
                bindingDonate.amount.setBackgroundColor(Color.parseColor("#FFFF3030"))
                Toast.makeText(requireContext(), "Please enter an amount", Toast.LENGTH_SHORT).show()
            }else{
                viewModel.donateAmount.value = bindingDonate.amount.text.toString()
                view.findNavController().navigate(R.id.donateConfirmFragment)

            }

        }


    }
    private fun setDataList() : ArrayList<DonateCardModel>{
        var arrayList : ArrayList<DonateCardModel> = ArrayList()

        arrayList.add(DonateCardModel(R.drawable.visa,1f,"Visa"))
        arrayList.add(DonateCardModel(R.drawable.mastercard_2,1f,"MasterCard"))
        arrayList.add(DonateCardModel(R.drawable.american_express_1,1f,"American Express"))
        arrayList.add(DonateCardModel(R.drawable.unionpay_seeklogo_com,1f,"Union Pay"))

        return arrayList
    }

    private fun setDataListAmount() : ArrayList<donateAmountModel>{
        var arrayList : ArrayList<donateAmountModel> = ArrayList()
        arrayList.add(donateAmountModel("RM 5","5"))
        arrayList.add(donateAmountModel("RM 10","10"))
        arrayList.add(donateAmountModel("RM 20","20"))
        arrayList.add(donateAmountModel("RM 50","50"))


        return arrayList
    }
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).findViewById<TextView>(R.id.top_toolbar_title).text = "Donate"
        (activity as AppCompatActivity?)!!.findViewById<Toolbar>(R.id.top_toolbar).visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.findViewById<Toolbar>(R.id.top_toolbar).visibility = View.VISIBLE
    }


}
