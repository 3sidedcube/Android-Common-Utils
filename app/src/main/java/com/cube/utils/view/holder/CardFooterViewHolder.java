package com.cube.utils.view.holder;

import android.view.ViewGroup;

import com.cube.utils.R;
import com.cube.utils.view.adapter.ListAdapter;

/**
 * Displays a layout designed to visually indicate the end of a group.
 *
 * @author Matt Allen
 * @project Utils
 */
public class CardFooterViewHolder extends ViewHolder<ListAdapter.GroupingItem>
{
	public CardFooterViewHolder(ViewGroup parent)
	{
		super(parent, R.layout.group_footer);
	}

	@Override public void populateView(ListAdapter.GroupingItem model)
	{

	}
}
