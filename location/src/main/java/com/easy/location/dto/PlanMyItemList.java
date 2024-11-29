package com.easy.location.dto;

import java.util.List;

public record PlanMyItemList(
    List<MyItemList> myItemList,
    List<PlanList> planList
){}
