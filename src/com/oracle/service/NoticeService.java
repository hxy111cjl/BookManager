package com.oracle.service;

import java.sql.SQLException;
import java.util.List;

import com.oracle.entity.Notice;
import com.oracle.mapper.NoticeDao;

public class NoticeService {
	private NoticeDao dao = new NoticeDao();

	// 后台系统，查询所有公告
	public List<Notice> getAllNotices() {
		return dao.getAllNotices();
	}

	// 后台系统，添加公告
	public int addNotice(Notice notice) {
		return dao.addNotice(notice);
	}

	// 后台系统，根据id查找公告
	public Notice findNoticeById(String n_id) {
		return dao.findNoticeById(n_id);
	}

	// 后台系统，根据id修改公告
	public int updateNotice(Notice bean) {
		return dao.updateNotice(bean);
	}

	// 后台系统，根据id删除公告
	public int deleteNotice(String n_id) {
		return dao.deleteNotice(n_id);
	}

	// 前台系统，查询最新添加或修改的一条公告
	public Notice getRecentNotice() {
		return dao.getRecentNotice();
	}
}
