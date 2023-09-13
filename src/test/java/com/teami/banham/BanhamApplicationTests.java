package com.teami.banham;

import com.teami.banham.dto.adoptDTO.AdoptResponseDto;
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
	BanhamApplicationTests(AdoptService adoptService) {
		this.adoptService = adoptService;
	}


	@Test
	void contextLoads() {
	}

	@Test
	void test () {
		List<AdoptResponseDto> list = adoptService.findMyAdopt(1L);
		System.out.println(list);

	}
}
