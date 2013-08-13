package com.slyak.core.web;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class OffsetLimitRequest implements Pageable {
	
	private int offset;
	
	private int limit;
	
	private Sort sort;
	
	public OffsetLimitRequest(int offset, int limit) {
		this.offset = offset;
		this.limit = limit;
	}

	public OffsetLimitRequest(int offset, int limit, Sort sort) {
		this(offset,limit);
		this.sort = sort;
	}

	@Override
	public int getPageNumber() {
		//no use
		return 0;
	}

	@Override
	public int getPageSize() {
		return this.limit;
	}

	@Override
	public int getOffset() {
		return this.offset;
	}

	@Override
	public Sort getSort() {
		return this.sort;
	}

}
