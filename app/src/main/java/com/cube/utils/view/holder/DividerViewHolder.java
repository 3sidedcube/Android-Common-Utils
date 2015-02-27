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
public class DividerViewHolder extends ViewHolder<ListAdapter.GroupingItem>
{
	public DividerViewHolder(ViewGroup parent)
	{
		super(parent, R.layout.divider);
	}

	@Override public void populateView(ListAdapter.GroupingItem model)
	{

	}
}
