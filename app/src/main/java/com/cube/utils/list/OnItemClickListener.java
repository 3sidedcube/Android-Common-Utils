package com.cube.utils.list;

/**
 * A simple item click interface that can be applied to a RecyclerView and respond to items
 * being selected. This will be attached upon creation.
 *
 * @author Matt Allen
 * @project Utils
 */
public interface OnItemClickListener
{
	public void onItemClick(int position, Object obj);
}
