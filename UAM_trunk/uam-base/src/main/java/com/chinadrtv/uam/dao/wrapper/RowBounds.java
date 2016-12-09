package com.chinadrtv.uam.dao.wrapper;

/**
 * 
 * @author Qianyong,Deng
 * @since Oct 8, 2012
 *
 */
public class RowBounds {

	public final static int NO_ROW_FIRST_RESULT = 0;
	public final static int NO_ROW_MAX_RESULTS = 0;
	public final static RowBounds DEFAULT = new RowBounds();

	private int firstResult;
	private int maxResults;

	public RowBounds() {
		this.firstResult = NO_ROW_FIRST_RESULT;
		this.maxResults = NO_ROW_MAX_RESULTS;
	}

	public RowBounds(int firstResult, int maxResults) {
		this.firstResult = firstResult;
		this.maxResults = maxResults;
	}

	public int getFirstResult() {
		return firstResult;
	}

	public int getMaxResults() {
		return maxResults;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof RowBounds))
			return false;
		RowBounds target = (RowBounds) obj;
		return target != null && this.firstResult == target.getFirstResult()
				&& this.maxResults == target.getMaxResults();
	}
	
	/**
	 * Check paging for the row bounds
	 * 
	 * @param rowBounds
	 * @return row bounds not null or not equal with RowBounds.DEFAULT
	 */
	public static boolean isPaging(RowBounds rowBounds) {
		return rowBounds != null && !DEFAULT.equals(rowBounds);
	}

}
