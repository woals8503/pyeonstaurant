package com.kh.Recipe.consult.service;

import java.util.List;

import com.kh.Recipe.consult.domain.Consult;
import com.kh.Recipe.consult.domain.ConsultServer;

public interface ConsultService {
	//리스트 전체 조회
		public List<ConsultServer> printAllChat();
	//고객채팅접수
		public int receiptChat(ConsultServer conServer);
	//고객 채팅DB저장
		public int inputChat(Consult consult);

}
