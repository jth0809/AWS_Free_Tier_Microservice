package com.easy.location.dto;

public record Traveldto (
    String id,

    String memberId,

    String title,

    String location,

    String startDate,

    String endDate,

    Long image,

    String status
) {}
