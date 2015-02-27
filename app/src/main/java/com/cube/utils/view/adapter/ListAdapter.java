package com.cube.utils.view.adapter;

import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;
import android.view.ViewGroup;

import com.cube.utils.view.OnItemClickListener;
import com.cube.utils.view.holder.ViewHolder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * A RecyclerView adapter which handles creating and binding view holders automatically using a {@link
 * com.cube.utils.view.adapter.ViewHolderManager}.
 *
 * @author Matt Allen
 * @project Utils
 */
public class ListAdapter extends Adapter<ViewHolder<?>>
{
	public enum GroupingItem
	{
		GROUP_HEADER,
		GROUP_FOOTER,
		GROUP_DIVIDER,
	}

	private List<Object> mObjects = new ArrayList<>();
	private OnItemClickListener mListener;
	private ViewHolderManager mTypeAdapter;

	public ListAdapter(ViewHolderManager typeAdapter)
	{
		mTypeAdapter = typeAdapter;
	}

	@Override public ViewHolder<?> onCreateViewHolder(ViewGroup viewGroup, int itemType)
	{
		Constructor<? extends ViewHolder<?>> viewHolderConstructor =
				mTypeAdapter.getViewHolderConstructorForItemType(itemType);

		if (viewHolderConstructor == null)
		{
			throw new IllegalStateException(
					"ItemTypeAdapter does not define a ViewHolder for items with type: " + itemType);
		}

		try
		{
			return viewHolderConstructor.newInstance(viewGroup);
		}
		catch (IllegalAccessException e)
		{
			throw new IllegalStateException(
					"ViewHolder constructor does not have accessible scope for items with type: " + itemType, e);
		}
		catch (InvocationTargetException | InstantiationException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override public int getItemCount()
	{
		return mObjects.size();
	}

	@Override public int getItemViewType(int position)
	{
		return mTypeAdapter.getItemType(mObjects.get(position));
	}

	@Override public void onBindViewHolder(final ViewHolder<?> viewHolder, final int i)
	{
		onBindViewHolderCast(viewHolder, i);

		if (mListener != null)
		{
			viewHolder.itemView.setOnClickListener(new View.OnClickListener()
			{
				@Override public void onClick(View v)
				{
					mListener.onItemClick(i, mObjects.get(i), viewHolder);
				}
			});
		}
	}

	/**
	 * This is a generically-typed version of the public method to allow casting of the object to a type compatible
	 * with
	 * the ViewHolder.
	 */
	private <T> void onBindViewHolderCast(ViewHolder<T> viewHolder, int i)
	{
		viewHolder.populateView((T)mObjects.get(i));
	}

	/**
	 * Clears all current items and adds the new items in the order they are encountered.
	 *
	 * @param items
	 * 		The items to be added, none of which can be null.
	 * @throws java.lang.IllegalArgumentException
	 * 		If any item is null or does not have an associated ViewHolder.
	 */
	public void setItems(Iterable<?> items)
	{
		mObjects.clear();
		addItems(items);
	}

	/**
	 * Adds the items in the order they are encountered.
	 *
	 * @param items
	 * 		The items to be added, none of which can be null.
	 * @throws java.lang.IllegalArgumentException
	 * 		If any item is null or does not have an associated ViewHolder.
	 */
	public void addItems(Iterable<?> items)
	{
		addItems(items, false);
	}

	/**
	 * Adds the items in the order they are encountered.
	 *
	 * @param items
	 * 		The items to be added, none of which can be null.
	 * @param addDividers
	 * 		If true, a {@link GroupingItem}.GROUP_DIVIDER will be placed between each pair of consecutive items.
	 * @throws java.lang.IllegalArgumentException
	 * 		If any item is null or does not have an associated ViewHolder.
	 */
	public void addItems(Iterable<?> items, boolean addDividers)
	{
		boolean firstItem = true;
		for (Object item : items)
		{
			if (addDividers && !firstItem)
			{
				addItem(GroupingItem.GROUP_DIVIDER);
			}
			else
			{
				firstItem = false;
			}
			addItem(item);
		}
	}

	/**
	 * Adds the given item to the end of the adapter. If the item subclasses {@code Iterable} its items will be
	 * flattened out into the adapter, prepended by a GROUP_HEADER item, and appended by a GROUP_FOOTER item.
	 *
	 * @param item
	 * 		The item to be added, which cannot be null and must have been associated with a ViewHolder.
	 * @throws java.lang.IllegalArgumentException
	 * 		If the item is null or does not have an associated ViewHolder.
	 */
	public void addItem(Object item)
	{
		if (item == null)
		{
			throw new IllegalArgumentException("Cannot add a null item to a ListAdapter");
		}

		if (item instanceof Iterable)
		{
			addItem(GroupingItem.GROUP_HEADER);
			addItems((Iterable<?>)item, true);
			addItem(GroupingItem.GROUP_FOOTER);
		}
		else
		{
			// Make sure the object has a view holder registered
			if (!mTypeAdapter.isRegistered(item))
			{
				throw new IllegalArgumentException(
						"Added item does not have an associated ViewHolder: " + item.getClass());
			}
			mObjects.add(item);
		}
	}

	/**
	 * Sets a listener to be alerted when the user clicks on a given view.
	 *
	 * @param listener
	 * 		Set to null to remove the listener.
	 */
	public void setOnItemClickListener(OnItemClickListener listener)
	{
		mListener = listener;
	}
}
