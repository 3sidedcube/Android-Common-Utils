package com.cube.utils.view.adapter;

import android.view.ViewGroup;

import com.cube.utils.view.holder.CardFooterViewHolder;
import com.cube.utils.view.holder.CardHeaderViewHolder;
import com.cube.utils.view.holder.ViewHolder;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * Dictates which {@link ViewHolder} should be used to display specific object instances, or entire classes of objects,
 * when using the view holder pattern.
 * <p/>
 * Associations are registered using one of {@link #registerItemType(Class, Class)} (for classes of objects) or {@link
 * #registerItemType(Object, Class)} (for specific object instances). In both cases the provided {@code ViewHolder}
 * class must:
 * <ul>
 * <li>...extend from {@link com.cube.utils.view.holder.ViewHolder}.</li>
 * <li>...define a single-argument constructor accepting a {@code ViewGroup} object to be used as the parent of
 * the created view.</li>
 * </ul>
 * <p/>
 * The class automatically assigns each {@code ViewHolder} a unique view type ID to be used with {@code RecyclerView},
 * to be retrieved with {@link #getItemType(Object)}.
 * To create a new view holder instance from a view type ID {@link #getViewHolderConstructorForItemType(int)} is
 * called.
 *
 * @author Matt Allen
 * @project Utils
 */
public class ViewHolderManager
{
	/**
	 * Simple holder class for internal use.
	 */
	private static class ViewHolderType
	{
		private static int NEXT_ITEM_TYPE = 0;

		private static synchronized int getNextItemType()
		{
			return NEXT_ITEM_TYPE++;
		}

		public int mItemType;
		public Constructor<? extends ViewHolder<?>> mViewHolderConstructor;

		ViewHolderType(Class<? extends ViewHolder<?>> viewHolderClass)
		{
			mItemType = getNextItemType();

			// get the constructor through reflection here as it may be a slow call
			try
			{
				mViewHolderConstructor = viewHolderClass.getConstructor(ViewGroup.class);
			}
			catch (NoSuchMethodException e)
			{
				throw new IllegalStateException("ViewHolder has not defined constructor taking a single ViewGroup parameter: " + viewHolderClass, e);
			}
		}
	}

	private Map<Object, ViewHolderType> mViewHoldersByObject;
	private Map<Integer, ViewHolderType> mViewHoldersByItemType;

	/**
	 * Create a new manager with default entries mapping {@link ListAdapter.GroupingItem}.GROUP_HEADER
	 * to {@link com.cube.utils.view.holder.CardHeaderViewHolder} and {@link ListAdapter.GroupingItem}.GROUP_FOOTER
	 * to {@link com.cube.utils.view.holder.CardFooterViewHolder}.
	 *
	 * @param expectedViewHolders
	 * 		Used to pre-allocate memory for storage.
	 */
	public ViewHolderManager(int expectedViewHolders)
	{
		mViewHoldersByObject = new HashMap<>(expectedViewHolders + 2);
		mViewHoldersByItemType = new HashMap<>(expectedViewHolders + 2);
		registerItemType(ListAdapter.GroupingItem.GROUP_HEADER, CardHeaderViewHolder.class);
		registerItemType(ListAdapter.GroupingItem.GROUP_FOOTER, CardFooterViewHolder.class);
	}

	/**
	 * Retrieves the item type for a given item.
	 *
	 * @param item
	 * @return
	 */
	public int getItemType(Object item)
	{
		ViewHolderType viewHolderType = mViewHoldersByObject.get(item);
		if (viewHolderType == null)
		{
			viewHolderType = mViewHoldersByObject.get(item.getClass());
		}
		return viewHolderType.mItemType;
	}

	/**
	 * Retrieves the view holder constructor for a particular item type.
	 *
	 * @param type
	 * @return
	 */
	public Constructor<? extends ViewHolder<?>> getViewHolderConstructorForItemType(int type)
	{
		return mViewHoldersByItemType.get(type).mViewHolderConstructor;
	}

	public boolean isRegistered(Object item)
	{
		return mViewHoldersByObject.containsKey(item) || mViewHoldersByObject.containsKey(item.getClass());
	}

	/**
	 * Registers a view holder for the given class of items. Only items of this class will be associated with it, unless
	 * they have a more specific view holder associated with them using {@link #registerItemType(Object, Class)}.
	 *
	 * @param item
	 * @param holderClass
	 * @param <T>
	 */
	public <T> void registerItemType(Class<T> itemClass, Class<? extends ViewHolder<? super T>> holderClass)
	{
		registerItemInternal(itemClass, holderClass);
	}

	/**
	 * Registers a view holder for the given item. Only items equal to {@code item} will be associated with it.
	 *
	 * @param item
	 * @param holderClass
	 * @param <T>
	 */
	public <T> void registerItemType(T item, Class<? extends ViewHolder<? super T>> holderClass)
	{
		registerItemInternal(item, holderClass);
	}

	/**
	 * This isn't publically exposed because it doesn't ensure the item is compatible with the view holder.
	 */
	private void registerItemInternal(Object item, Class<? extends ViewHolder<?>> holderClass)
	{
		ViewHolderType viewHolderType = new ViewHolderType(holderClass);
		mViewHoldersByObject.put(item, viewHolderType);
		mViewHoldersByItemType.put(viewHolderType.mItemType, viewHolderType);
	}
}
