package com.teami.banham.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.teami.banham.repository.MemberRepository;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final MemberRepository memberRepository;

}
