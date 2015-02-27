package com.cube.utils.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Base class for a view holder to be used with {@link com.cube.utils.view.adapter.ViewHolderManager}.
 * <p/>
 * Subclasses must have a single-argument constructor taking a {@code ViewGroup}.
 *
 * @author Matt Allen
 * @project Utils
 */
public abstract class ViewHolder<T> extends RecyclerView.ViewHolder
{
	public ViewHolder(ViewGroup parent, int layoutResource)
	{
		super(LayoutInflater.from(parent.getContext()).inflate(layoutResource, parent, false));
	}

	/**
	 * Populate the view to display the data held in the provided object.
	 *
	 * @param model
	 */
	public abstract void populateView(T model);
}
