package com.cube.utils.list.adapter;

import android.support.v7.widget.RecyclerView.Adapter;
import android.view.ViewGroup;

import com.cube.utils.list.OnItemClickListener;
import com.cube.utils.list.holder.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * // TODO: Add class description
 *
 * @author Matt Allen
 * @project Utils
 */
public class ListAdapter extends Adapter<com.cube.utils.list.holder.ViewHolder>
{
	private List<Object> mObjects;
	private OnItemClickListener mListener;
	private ItemTypeAdapter mTypeAdapter;

	private enum GroupItems
	{
		CARD_HEADER(0),CARD_FOOTER(1);

		private int type;

		GroupItems(int type)
		{
			this.type = type;
		}
	}

	@Override public com.cube.utils.list.holder.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
	{
		Class<? extends ViewHolder> viewHolderClass = mTypeAdapter.getHolderForType(i);
		try
		{
			return viewHolderClass.getConstructor(ViewGroup.class).newInstance(viewGroup);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override public int getItemCount()
	{
		return mObjects != null ? mObjects.size() : 0;
	}

	@Override public void onBindViewHolder(com.cube.utils.list.holder.ViewHolder viewHolder, int i)
	{

	}

	public void setItems(List<Object> items)
	{
		for (Object item : items)
		{
			if (item instanceof List)
			{
				getItems().add(GroupItems.CARD_HEADER);
				setItems((List<Object>)item);
				getItems().add(GroupItems.CARD_FOOTER);
			}
			else
			{
				getItems().add(item);
			}
		}
	}

	public void setItemTypeAdapter(ItemTypeAdapter adapter)
	{
		mTypeAdapter = adapter;
	}

	public List<Object> getItems()
	{
		if (mObjects == null)
		{
			mObjects = new ArrayList<>();
		}
		return mObjects;
	}

	@Override public int getItemViewType(int position)
	{
		return mTypeAdapter.getIndexForClassType(mObjects.get(position).getClass());
	}
}
