package com.teami.banham;

import com.teami.banham.dto.adoptDTO.AdoptCommentResponse;
import com.teami.banham.dto.adoptDTO.AdoptIndex;
import com.teami.banham.dto.adoptDTO.AdoptResponseDto;
import com.teami.banham.service.adoptService.AdoptCommentService;
import com.teami.banham.service.adoptService.AdoptService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BanhamApplicationTests {

	@Autowired
	private final AdoptService adoptService;
	@Autowired
	private final AdoptCommentService commentService;
	@Autowired
	BanhamApplicationTests(AdoptService adoptService, AdoptCommentService commentService) {
		this.adoptService = adoptService;
		this.commentService = commentService;
	}


	@Test
	void contextLoads() {
	}

	@Test
	void test () {
		List<AdoptResponseDto> list = adoptService.findMyAdopt(1L);
		System.out.println(list);

	}
	@Test
	void indextest() {
		List<AdoptIndex> list = adoptService.findAdoptIndex();
		System.out.println(list);
	}
	@Test
	void i(){
		List<AdoptCommentResponse> f = commentService.findAdoptComment(1L);

	}
}
