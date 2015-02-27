package com.cube.utils.view.holder;

import android.view.ViewGroup;

import com.cube.utils.R;
import com.cube.utils.view.adapter.ListAdapter;

/**
 * Displays a layout designed to visually indicate a new group.
 *
 * @author Matt Allen
 * @project Utils
 */
public class CardHeaderViewHolder extends ViewHolder<ListAdapter.GroupingItem>
{
	public CardHeaderViewHolder(ViewGroup parent)
	{
		super(parent, R.layout.group_header);
	}

	@Override public void populateView(ListAdapter.GroupingItem model)
	{

	}
}
