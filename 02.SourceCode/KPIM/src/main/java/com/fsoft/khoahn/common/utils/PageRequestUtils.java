package com.fsoft.khoahn.common.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.fsoft.khoahn.dto.req.PaginationReqDto;
import com.fsoft.khoahn.dto.req.SortReqDto;

public class PageRequestUtils {

	public static Pageable createPageRequest(PaginationReqDto paginationReqDto, List<SortReqDto> sortReqDtos) {
		Pageable pageable;
		if (sortReqDtos != null && !sortReqDtos.isEmpty()) {
			List<Order> orders = new ArrayList<>();
			for (Iterator<SortReqDto> iterator = sortReqDtos.iterator(); iterator.hasNext();) {
				SortReqDto sortReqDto = iterator.next();
				Order order = new Order(Direction.fromString(sortReqDto.getOrder()), sortReqDto.getFieldName());
				orders.add(order);
			}
			Sort sort = Sort.by(orders);
			pageable = PageRequest.of(paginationReqDto.getPageNumber() - 1, paginationReqDto.getPageSize(), sort);
		} else {
			pageable = PageRequest.of(paginationReqDto.getPageNumber() - 1, paginationReqDto.getPageSize());
		}
		return pageable;
	}
}