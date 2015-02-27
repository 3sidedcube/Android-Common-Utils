package com.cube.utils.view;

import com.cube.utils.view.holder.ViewHolder;

/**
 * A simple item click interface that can be applied to a RecyclerView and respond to items
 * being selected. This will be attached upon creation.
 *
 * @author Matt Allen
 * @project Utils
 */
public interface OnItemClickListener
{
	void onItemClick(int position, Object obj, ViewHolder<?> viewHolder);
}
