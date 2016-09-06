package com.example.basepackagelib.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.basepackagelib.R;

public class RoundImageView extends ImageView {
	// Border & Selector configuration variables
	private int canvasSize;

	// Objects used for the actual drawing
	private BitmapShader shader;
	private Bitmap image;
	private Paint paint;

	private float cx;
	private float cy;
	private float radius;
	
	public RoundImageView(Context context) {
		this(context, null);
	}

	public RoundImageView(Context context, AttributeSet attrs) {
		this(context, attrs,
				R.styleable.CircularImageViewStyle_circularImageViewDefault);
	}

	public RoundImageView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs, defStyle);
	}

	/**
	 * Initializes paint objects and sets desired attributes.
	 * 
	 * @param context
	 *            Context
	 * @param attrs
	 *            Attributes
	 * @param defStyle
	 *            Default Style
	 */
	private void init(Context context, AttributeSet attrs, int defStyle) {
		// Initialize paint objects
		paint = new Paint();
		paint.setAntiAlias(true);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		// Don't draw anything without an image
		if (image == null)
			return;

		// Nothing to draw (Empty bounds)
		if (image.getHeight() == 0 || image.getWidth() == 0)
			return;

		// We'll need this later
		int oldCanvasSize = canvasSize;

		// Compare canvas sizes
		canvasSize = getWidth();
		if (getHeight() < canvasSize) {
			canvasSize = getHeight();
		}

		// Reinitialize shader, if necessary
		if (oldCanvasSize != canvasSize) {
			refreshBitmapShader();
		}

		// Apply shader to paint
		paint.setShader(shader);

		// Keep track of selectorStroke/border width
		int outerWidth = 0;

		// Get the exact X/Y axis of the view
		radius = canvasSize / 2;
		cx = radius + outerWidth;
		cy = radius + outerWidth;
		paint.setColorFilter(null);

		// Draw the circular image itself
		canvas.drawCircle(cx, cy, ((canvasSize - (outerWidth * 2)) / 2) - 4.0f,
				paint);
	}
	
	@Override
	public void invalidate() {
		super.invalidate();

		// Don't do anything without a valid drawable
		if (getDrawable() == null)
			return;

		// Extract a Bitmap out of the drawable & set it as the main shader
		image = drawableToBitmap(getDrawable());
		if (shader != null || canvasSize > 0)
			refreshBitmapShader();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = measureWidth(widthMeasureSpec);
		int height = measureHeight(heightMeasureSpec);

		width = Math.min(width, height);
		height = width;

		setMeasuredDimension(width, height);
	}

	private int measureWidth(int measureSpec) {
		int result;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			// The parent has determined an exact size for the child.
			result = specSize;
		} else if (specMode == MeasureSpec.AT_MOST) {
			// The child can be as large as it wants up to the specified size.
			result = specSize;
		} else {
			// The parent has not imposed any constraint on the child.
			result = canvasSize;
		}

		return result;
	}

	private int measureHeight(int measureSpecHeight) {
		int result;
		int specMode = MeasureSpec.getMode(measureSpecHeight);
		int specSize = MeasureSpec.getSize(measureSpecHeight);

		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be
			result = specSize;
		} else if (specMode == MeasureSpec.AT_MOST) {
			// The child can be as large as it wants up to the specified size.
			result = specSize;
		} else {
			// Measure the text (beware: ascent is a negative number)
			result = canvasSize;
		}

		return (result + 2);
	}

	/**
	 * Convert a drawable object into a Bitmap.
	 * 
	 * @param drawable
	 *            Drawable to extract a Bitmap from.
	 * @return A Bitmap created from the drawable parameter.
	 */
	public Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable == null) // Don't do anything without a proper drawable
			return null;
		else if (drawable instanceof BitmapDrawable) // Use the getBitmap()
														// method instead if
														// BitmapDrawable
			return ((BitmapDrawable) drawable).getBitmap();

		// Create Bitmap object out of the drawable
		int intrinsicW=drawable.getIntrinsicWidth();
		int intrinsicH=drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(intrinsicW,intrinsicH, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);
		// Return the created Bitmap
		return bitmap;
	}

	/**
	 * Reinitializes the shader texture used to fill in the Circle upon drawing.
	 */
	private void refreshBitmapShader() {
		shader = new BitmapShader(Bitmap.createScaledBitmap(image, canvasSize,
				canvasSize, false), Shader.TileMode.CLAMP,
				Shader.TileMode.CLAMP);
	}

}
