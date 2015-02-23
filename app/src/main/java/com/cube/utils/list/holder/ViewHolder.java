package com.cube.utils.list.holder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

/**
 * // TODO: Add class description
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

	public abstract void populateView(T model);

	public void setOnItemClickListener(OnClickListener listener)
	{
		this.itemView.setOnClickListener(listener);
	}
}
