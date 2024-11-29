package com.easy.location.dto;


public record MyItem (
    Long imageColor,  // 임시 사진 배경
    String title, // 여행 제목
    String startDate, // 시작날짜
    String endDate, // 도착날짜
    String location, // 장소
    String statusText,
    String plannerId
){}
