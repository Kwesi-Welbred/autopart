/*
package com.android.automobile.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.automobile.R
import com.android.automobile.model.Accessories

class MultiRecyclerAdapter(private val context: Context, list: ArrayList<Accessories>) :
	RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	companion object {
		const val THE_FIRST_VIEW = 1
		const val THE_SECOND_VIEW = 2
	}

	private val yourContext: Context = context
	var list: ArrayList<Accessories> = list

	private inner class ViewHolderOne(itemView: View) :
		RecyclerView.ViewHolder(itemView) {
		var gfgText: TextView = itemView.findViewById(R.id.productPrice)
		fun bind(position: Int) {
			val recyclerViewModel = list[position]
			gfgText.text = recyclerViewModel.brandName
		}
	}

	private inner class ViewHolder2(itemView: View) :
		RecyclerView.ViewHolder(itemView) {
		var gfgText: TextView = itemView.findViewById(R.id.total_amount)
		fun bind(position: Int) {
			val recyclerViewModel = list[position]
			gfgText.text = recyclerViewModel.price
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		if (viewType == VIEW_TYPE_ONE) {
			return ViewHolderOne(
				LayoutInflater.from(context).inflate(R.layout.checkout_list, parent, false)
			)
		}
		return ViewHolder2(
			LayoutInflater.from(context).inflate(R.layout.product_list, parent, false)
		)
	}

	override fun getItemCount(): Int {
		return list.size
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		if (list[position].viewType === VIEW_TYPE_ONE) {
			(holder as ViewHolderOne).bind(position)
		} else {
			(holder as ViewHolder2).bind(position)
		}
	}

	override fun getItemViewType(position: Int): Int {
		return list[position].viewType
	}
}
*/
