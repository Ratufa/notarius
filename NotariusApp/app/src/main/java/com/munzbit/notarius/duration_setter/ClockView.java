package com.munzbit.notarius.duration_setter;

import static com.munzbit.notarius.duration_setter.CircularClockSeekBar.ClockRangeStatus.VALID_RANGE;
import static com.munzbit.notarius.duration_setter.Utils.SIMPLE_DATE_FORMAT_AM_PM;
import static com.munzbit.notarius.duration_setter.Utils.SIMPLE_DATE_FORMAT_HOURS;

import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.Interval;
import org.joda.time.Minutes;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.munzbit.notarius.R;
import com.munzbit.notarius.data_manager.SharedPrefrnceNotarius;

public class ClockView extends LinearLayout {

	public static final float LETTER_SPACING = -3.0f;

	private TextView mTimeText;
	// private final LetterSpacingTextView mTimeMeridianText;
	// private final RobotoTextView mTimeWeekDayText;
	private CircularClockSeekBar mCircularClockSeekBar;

	private Interval mValidTimeInterval;

	private DateTime mOriginalTime;

	private final boolean mIs24HourFormat;

	private DateTime mNewCurrentTime;

	private int mCurrentValidProgressDelta;

	private DateTime mCurrentValidTime;

	private ClockTimeUpdateListener mClockTimeUpdateListener;
	
	int position=0;

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	@SuppressLint("NewApi")
	public ClockView(Context context, AttributeSet attrs) {
		super(context, attrs);

		mClockTimeUpdateListener = new ClockTimeUpdateListener() {
			@Override
			public void onClockTimeUpdate(ClockView clockView,
					DateTime currentTime) {
			}
		};
		mIs24HourFormat = DateFormat.is24HourFormat(context);
		
		View view = null;
		// if(isInEditMode()) {
		view = LayoutInflater.from(context).inflate(R.layout.clock_view, this);
		
		mTimeText = (TextView) view.findViewById(R.id.time_text_view);
		
		mCircularClockSeekBar = (CircularClockSeekBar) view
				.findViewById(R.id.clock_seek_bar);

		mCircularClockSeekBar
				.setSeekBarChangeListener(new CircularClockSeekBar.OnSeekBarChangeListener() {
					@Override
					public void onProgressChanged(CircularClockSeekBar seekBar,
							int progress, boolean fromUser) {

						updateProgressWithDelta(seekBar.getProgressDelta());
						//seekBar.setProgress(progress);
						//mNewCurrentTime = mOriginalTime.plusMinutes((seekBar.getProgressDelta() / 30) * 5);
//						int hourCheck;
//						if(Integer.parseInt(SIMPLE_DATE_FORMAT_AM_PM.print(mNewCurrentTime)) == 12){
//							hourCheck=0;
//						}else{
//							hourCheck = Integer.parseInt(SIMPLE_DATE_FORMAT_AM_PM.print(mNewCurrentTime));
//						}
//						seekBar.setCircleProgressColor(Color.parseColor(getResources().getStringArray(R.array.custom_colors)[hourCheck]));
						
					}

					@Override
					public void onStartTrackingTouch(
							CircularClockSeekBar seekBar) {
						
					}

					@Override
					public void onStopTrackingTouch(CircularClockSeekBar seekBar) {
						// snap to correct position
						if (mValidTimeInterval.contains(mNewCurrentTime)) {
							// snap to nearest hour.
							int progressDelta = (int) (mCircularClockSeekBar
									.getAngle() % 360);
							if (progressDelta != 0) {
								// snap either to previous/next hour
								// mCircularClockSeekBar.roundToNearestDegree(30);
							} else {
								// we are triggering onClockTimeUpdate because
								// the user was perfect and moved the
								// clock by exact multiple of 30 degrees so
								// there is no animation and the time is still
								// within valid time interval
								mClockTimeUpdateListener.onClockTimeUpdate(
										ClockView.this, mNewCurrentTime);
							}
						} else {
							// slide-animate back or forward
							mCircularClockSeekBar.animateToDelta(
									mCircularClockSeekBar.getProgressDelta(),
									mCurrentValidProgressDelta);
							setClockText(mCurrentValidTime,position);
						}
					}

					

					@Override
					public void onAnimationComplete(
							CircularClockSeekBar seekBar) {
						// TODO Auto-generated method stub
						if (mValidTimeInterval != null
								&& mNewCurrentTime != null
								&& mValidTimeInterval.contains(mNewCurrentTime)) {
							mClockTimeUpdateListener.onClockTimeUpdate(
									ClockView.this, mNewCurrentTime);
						}
					}
				});
		// }
		// mTimeMeridianText = (LetterSpacingTextView)
		// view.findViewById(R.id.time_meredian_text_view);
		// mTimeWeekDayText = (RobotoTextView)
		// view.findViewById(R.id.time_week_day_text);
		if (isInEditMode()) {
			mTimeText.setLetterSpacing(LETTER_SPACING);
			// mTimeMeridianText.setTypeface(Utils.getRobotoTypeface(context,
			// REGULAR));
			// mTimeMeridianText.setLetterSpacing(LETTER_SPACING);
		}

	}

	public interface ClockTimeUpdateListener {
		public void onClockTimeUpdate(ClockView clockView, DateTime currentTime);
	}

	public void setNewCurrentTime(DateTime newCurrentTime) {
		if (mValidTimeInterval != null && newCurrentTime != null
				&& mNewCurrentTime != null
				&& mValidTimeInterval.contains(newCurrentTime)) {
			int diffInMinutes = Minutes.minutesBetween(mNewCurrentTime,
					newCurrentTime).getMinutes();
			mCircularClockSeekBar.moveToDelta(mCurrentValidProgressDelta,
					diffInMinutes / 2);
			setClockText(mCurrentValidTime,position);
		}
	}

	private void updateProgressWithDelta(int progressDelta) {
		// 1 deg = 2 min
		
		//Log.e("progress delta", progressDelta / 24 + ">>");
		position = progressDelta / 24;
		mNewCurrentTime = mOriginalTime.plusMinutes((progressDelta / 30) * 5);
		setClockText(mNewCurrentTime,position);
		/*if (mValidTimeInterval != null && mNewCurrentTime != null
				&& mValidTimeInterval.contains(mNewCurrentTime)) {
			mCurrentValidProgressDelta = progressDelta;
			mCurrentValidTime = mNewCurrentTime
					.minusMinutes((progressDelta / 30) * 5);
		}*/
	}

	private void setClockText(DateTime newCurrentTime,int pp) {
		// String timeStr="";
		int hourCheck=0;
		if(Integer.parseInt(SIMPLE_DATE_FORMAT_AM_PM.print(newCurrentTime)) == 12){
			hourCheck=0;
		}else{
			hourCheck = Integer.parseInt(SIMPLE_DATE_FORMAT_AM_PM.print(newCurrentTime));
		}
		
		if (mIs24HourFormat) {
			
			if (hourCheck > 0){
				mTimeText.setText(SIMPLE_DATE_FORMAT_AM_PM
						.print(newCurrentTime)
						+ "h "
						+ newCurrentTime.getMinuteOfHour() + "min");
				SharedPrefrnceNotarius.setDataInSharedPrefrence(getContext(),
						"time_Str", SIMPLE_DATE_FORMAT_HOURS.print(newCurrentTime)
								+ "h " + newCurrentTime.getMinuteOfHour()
								+ "min");
			}
			else{
				mTimeText.setText(newCurrentTime.getMinuteOfHour() + "min");
				SharedPrefrnceNotarius.setDataInSharedPrefrence(getContext(),
						"time_Str", newCurrentTime.getMinuteOfHour()
								+ "min");
			}
			
			
			// timeStr
			// =SIMPLE_DATE_FORMAT_HOURS.print(newCurrentTime)+" hr : "+newCurrentTime.getMinuteOfHour()+" min";
			// mTimeMeridianText.setText(R.string.flights_app_short_hrs_cbd);
		} else {
			
			if (hourCheck > 0){
				mTimeText.setText(SIMPLE_DATE_FORMAT_AM_PM
						.print(newCurrentTime)
						+ "h "
						+ newCurrentTime.getMinuteOfHour() + "min");
				SharedPrefrnceNotarius.setDataInSharedPrefrence(getContext(),
						"time_Str", SIMPLE_DATE_FORMAT_HOURS.print(newCurrentTime)
								+ "h " + newCurrentTime.getMinuteOfHour()
								+ "min");
			}
			else{
				mTimeText.setText(newCurrentTime.getMinuteOfHour() + "min");
				SharedPrefrnceNotarius.setDataInSharedPrefrence(getContext(),
						"time_Str", newCurrentTime.getMinuteOfHour()
								+ "min");
			}
			
		}
		// Log.e("timeStr>>>",
		// timeStr+">>>"+Integer.parseInt(SIMPLE_DATE_FORMAT_AM_PM.print(newCurrentTime)));
		setSeekBarStatus(
				newCurrentTime,
				Integer.parseInt(SIMPLE_DATE_FORMAT_AM_PM.print(newCurrentTime)),pp);
	}

	private void setSeekBarStatus(DateTime newCurrentTime, int hours,int pp) {
		if (mValidTimeInterval.contains(newCurrentTime)) {
			if (newCurrentTime.getDayOfWeek() == mValidTimeInterval.getStart()
					.getDayOfWeek()) {
				mCircularClockSeekBar.setSeekBarStatus(VALID_RANGE, hours,pp);
				// mTimeWeekDayText.setVisibility(GONE);
			} else {
				// mCircularClockSeekBar.setSeekBarStatus(DIFFERENT_DAY_OF_WEEK,hours);
				// mTimeWeekDayText.setVisibility(VISIBLE);
				// mTimeWeekDayText.setText(newCurrentTime.toString("EEE"));
			}
		} else {
			// mCircularClockSeekBar.setSeekBarStatus(INVALID_RANGE);
			// mTimeWeekDayText.setVisibility(GONE);
		}
	}

	public void setBounds(DateTime minTime, DateTime maxTime, boolean isMaxClock) {
		// NOTE: To show correct end time on clock, since the
		// Interval.contains() checks for
		// millisInstant >= thisStart && millisInstant < thisEnd
		// however we want
		// millisInstant >= thisStart && millisInstant <= thisEnd
		// if(isInEditMode()) {
		maxTime = maxTime.plusMillis(1);
		mValidTimeInterval = new Interval(minTime, maxTime);
		maxTime = maxTime.minusMillis(1);
		mCircularClockSeekBar.reset();
		if (isMaxClock) {
			mOriginalTime = maxTime;
			mCurrentValidTime = maxTime;
			int hourOfDay = minTime.get(DateTimeFieldType.clockhourOfDay()) % 12;
			// Log.e("hour of day>>1", hourOfDay + "?>>");
			mCircularClockSeekBar.setProgress(hourOfDay * 10);
			setClockText(mOriginalTime,position);
		} else {
			mOriginalTime = minTime;
			mCurrentValidTime = minTime;
			int hourOfDay = minTime.get(DateTimeFieldType.clockhourOfDay()) % 12;
			// int hourOfDay = (maxTime.get(DateTimeFieldType.minuteOfDay()) /
			// 5)*5;
			// Log.e("hour of day>>2", hourOfDay + "?>>");
			mCircularClockSeekBar.setProgress(hourOfDay * 10);
			// mCircularClockSeekBar.setProgress(((hourOfDay * 5)/5));
			setClockText(mOriginalTime,position);
		}
		// }
	}

	@Nullable
	@Override
	public CharSequence getContentDescription() {
		return String.format("%s%s", mTimeText.getText(), "");
	}

	public void setClockTimeUpdateListener(
			ClockTimeUpdateListener clockTimeUpdateListener) {
		mClockTimeUpdateListener = clockTimeUpdateListener;
	}

	public void removeClockTimeUpdateListener(
			ClockTimeUpdateListener clockTimeUpdateListener) {
		if (mClockTimeUpdateListener.equals(clockTimeUpdateListener)) {
			mClockTimeUpdateListener = new ClockTimeUpdateListener() {
				@Override
				public void onClockTimeUpdate(ClockView clockView,
						DateTime currentTime) {
				}
			};
		}
	}

	public DateTime getCurrentValidTime() {
		return mCurrentValidTime;
	}

	public DateTime getNewCurrentTime() {
		return mNewCurrentTime;
	}
}
