package com.example.basepackagelib.parcelable;

import java.lang.reflect.Field;

import android.os.Parcel;
import android.os.Parcelable;

public class AutoParcelable implements Parcelable {
	public static Parcelable.Creator<?> CREATOR=null;
	
	protected AutoParcelable() {
	}
	
	protected AutoParcelable(Parcel in) {
		readToParcel(in);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	protected void readToParcel(Parcel dest) {
		Field[] fields = getClass().getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				try {
					if (field.getType().isAssignableFrom(String.class)) {
						field.set(this, dest.readString());
					}

					if (field.getType().isAssignableFrom(Integer.class)||field.getType().isAssignableFrom(int.class)) {
						int intdest=dest.readInt();
						field.set(this,intdest );
					}

					if (field.getType().isAssignableFrom(Float.class)||field.getType().isAssignableFrom(float.class)) {
						field.set(this, dest.readFloat());
					}

//					if (field.getType().isAssignableFrom(ArrayList.class)) {
//						Parcelable.Creator<?> creator=getCreator(field);
//						field.set(this, dest.createTypedArrayList(creator));
//					}

					Class<?>[] interfaces = field.getType().getInterfaces();
					for (Class<?> class1 : interfaces) {
						if (class1.getName().equals(Parcelable.class.getName())) {
							field.set(this, dest.readParcelable(field.getClass().getClassLoader()));
							break;
						}
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private Parcelable.Creator<?> getCreator(Field parent){
		Field[] childfields=parent.getClass().getDeclaredFields();
		for (Field field : childfields) {
			if (field.getType().getSimpleName().equals(Parcelable.Creator.class.getSimpleName())) {
				try {
					Parcelable.Creator<?> creater=(Creator<?>) field.get(this);
					return creater;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public void writeToParcel(Parcel dest, int flag) {
		Field[] fields = getClass().getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				try {
					if (field.getType().isAssignableFrom(String.class)) {
						dest.writeString((String) field.get(this));
					}

					if (field.getType().isAssignableFrom(Integer.class)||field.getType().isAssignableFrom(int.class)) {
						dest.writeInt((Integer) field.get(this));
					}

					if (field.getType().isAssignableFrom(Float.class)||field.getType().isAssignableFrom(float.class)) {
						dest.writeFloat((Float) field.get(this));
					}

//					if (field.getType().isAssignableFrom(ArrayList.class)) {
//						ArrayList<Parcelable> list = (ArrayList<Parcelable>) field
//								.get(this);
//						dest.writeTypedList(list);
//					}

					Class<?>[] interfaces = field.getType().getInterfaces();
					for (Class<?> class1 : interfaces) {
						if (class1.getName().equals(Parcelable.class.getName())) {
							dest.writeParcelable((Parcelable) field.get(this),
									flag);
							break;
						}
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
