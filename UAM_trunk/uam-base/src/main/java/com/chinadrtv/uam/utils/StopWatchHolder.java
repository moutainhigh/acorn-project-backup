package com.chinadrtv.uam.utils;

import org.apache.commons.lang.time.StopWatch;

/**
 * 
 * @author Qianyong,Deng
 * @since Oct 9, 2012
 *
 */
public class StopWatchHolder {

	public static final ThreadLocal<StopWatch> holder = new ThreadLocal<StopWatch>();

	/**
	 * <p>
	 * Start the stopwatch.
	 * </p>
	 * 
	 * <p>
	 * This method starts a new timing session, clearing any previous values.
	 * </p>
	 * 
	 * @throws IllegalStateException
	 *             if the StopWatch is already running.
	 */
	public static void start() {
		StopWatch stopWatch = holder.get();
		if (stopWatch == null) {
			stopWatch = new StopWatch();
			holder.set(stopWatch);
		}
		try {
			stopWatch.start();
		} catch (Exception e) {
		}
	}

	/**
	 * <p>
	 * Remove the stopwatch.
	 * </p>
	 */
	public static void remove() {
		holder.remove();
	}

	/**
	 * <p>
	 * Stop the stopwatch.
	 * </p>
	 * 
	 * <p>
	 * This method ends a new timing session, allowing the time to be retrieved.
	 * </p>
	 * 
	 * @throws IllegalStateException
	 *             if the StopWatch is not running.
	 */
	public static void stop() {
		StopWatch stopWatch = holder.get();
		if (stopWatch != null) {
			try {
				stopWatch.stop();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * <p>
	 * Resets the stopwatch. Stops it if need be.
	 * </p>
	 * 
	 * <p>
	 * This method clears the internal values to allow the object to be reused.
	 * </p>
	 */
	public static void reset() {
		StopWatch stopWatch = holder.get();
		if (stopWatch != null) {
			try {
				stopWatch.reset();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * <p>
	 * Split the time.
	 * </p>
	 * 
	 * <p>
	 * This method sets the stop time of the watch to allow a time to be
	 * extracted. The start time is unaffected, enabling {@link #unsplit()} to
	 * continue the timing from the original start point.
	 * </p>
	 * 
	 * @throws IllegalStateException
	 *             if the StopWatch is not running.
	 */
	public static void split() {
		StopWatch stopWatch = holder.get();
		if (stopWatch != null) {
			try {
				stopWatch.split();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * <p>
	 * Remove a split.
	 * </p>
	 * 
	 * <p>
	 * This method clears the stop time. The start time is unaffected, enabling
	 * timing from the original start point to continue.
	 * </p>
	 * 
	 * @throws IllegalStateException
	 *             if the StopWatch has not been split.
	 */
	public static void unsplit() {
		StopWatch stopWatch = holder.get();
		if (stopWatch != null) {
			try {
				stopWatch.unsplit();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * <p>
	 * Suspend the stopwatch for later resumption.
	 * </p>
	 * 
	 * <p>
	 * This method suspends the watch until it is resumed. The watch will not
	 * include time between the suspend and resume calls in the total time.
	 * </p>
	 * 
	 * @throws IllegalStateException
	 *             if the StopWatch is not currently running.
	 */
	public static void suspend() {
		StopWatch stopWatch = holder.get();
		if (stopWatch != null) {
			try {
				stopWatch.suspend();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * <p>
	 * Resume the stopwatch after a suspend.
	 * </p>
	 * 
	 * <p>
	 * This method resumes the watch after it was suspended. The watch will not
	 * include time between the suspend and resume calls in the total time.
	 * </p>
	 * 
	 * @throws IllegalStateException
	 *             if the StopWatch has not been suspended.
	 */
	public static void resume() {
		StopWatch stopWatch = holder.get();
		if (stopWatch != null) {
			try {
				stopWatch.resume();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * <p>
	 * Get the time on the stopwatch.
	 * </p>
	 * 
	 * <p>
	 * This is either the time between the start and the moment this method is
	 * called, or the amount of time between start and stop.
	 * </p>
	 * 
	 * @return the time in milliseconds
	 */
	public static long getTime() {
		StopWatch stopWatch = holder.get();
		if (stopWatch != null)
			return stopWatch.getTime();
		return -1L;
	}

	/**
	 * <p>
	 * Get the split time on the stopwatch.
	 * </p>
	 * 
	 * <p>
	 * This is the time between start and latest split.
	 * </p>
	 * 
	 * @return the split time in milliseconds
	 * 
	 * @throws IllegalStateException
	 *             if the StopWatch has not yet been split.
	 * @since 2.1
	 */
	public static long getSplitTime() {
		StopWatch stopWatch = holder.get();
		if (stopWatch != null)
			return stopWatch.getSplitTime();
		return -1L;
	}

	/**
	 * Returns the time this stopwatch was started.
	 * 
	 * @return the time this stopwatch was started
	 * @throws IllegalStateException
	 *             if this StopWatch has not been started
	 * @since 2.4
	 */
	public static long getStartTime() {
		StopWatch stopWatch = holder.get();
		if (stopWatch != null)
			return stopWatch.getStartTime();
		return -1L;
	}
}
