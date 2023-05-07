package com.example.foodhub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodhub.databinding.FragmentDonateBinding

class DonateFragment : Fragment() {

    private lateinit var bindingDonate: FragmentDonateBinding

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

        rview.setHasFixedSize(true)
        var arrayList : ArrayList<DonateCardModel> = setDataList()
        var adapter1 = DonateCardAdapter(arrayList)
        rview.adapter = adapter1


        adapter1.onItemClick={
            donateCardModel ->
            for (i in arrayList) {
                if (i.name != donateCardModel.name) {
                    i.status = 1f
                }else{
                    i.status = 0.6f
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
            view.findNavController().navigate(R.id.donateConfirmFragment)
        }


    }
    private fun setDataList() : ArrayList<DonateCardModel>{
        var arrayList : ArrayList<DonateCardModel> = ArrayList()
        arrayList.add(DonateCardModel(R.drawable.touch__n_go_logo_1,"+60** **** ****",1f,"Touch n Go"))
        arrayList.add(DonateCardModel(R.drawable.visa,"**** **** **** 0000",1f,"Visa"))
        arrayList.add(DonateCardModel(R.drawable.mastercard_2,"**** **** **** 0000",1f,"MasterCard"))
        arrayList.add(DonateCardModel(R.drawable.bitcoin_logo,"**** **** ****",1f,"Bitcoin"))

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
        (activity as AppCompatActivity?)!!.findViewById<Toolbar>(R.id.top_toolbar).visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.findViewById<Toolbar>(R.id.top_toolbar).visibility = View.VISIBLE
    }


}
