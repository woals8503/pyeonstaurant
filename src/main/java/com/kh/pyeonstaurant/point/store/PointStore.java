package com.kh.pyeonstaurant.point.store;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import com.kh.pyeonstaurant.point.domain.Point;

public interface PointStore {
	public List<Point> selectAllPoint(String pointMemberEmail, SqlSessionTemplate session);
}
