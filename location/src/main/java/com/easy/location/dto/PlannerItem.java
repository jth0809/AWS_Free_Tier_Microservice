package com.easy.location.dto;

public record PlannerItem(
    String title, // 일정 제목
    String startTime,  // 시작 시간
    String endTime, // 종료 시간
    String location, // 장소
    String memo, // 일정 메모
    Double latitude,  // 추가
    Double longitude  // 추가
) {}