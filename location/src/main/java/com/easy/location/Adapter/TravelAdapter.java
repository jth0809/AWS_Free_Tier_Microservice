package com.easy.location.Adapter;

import org.springframework.stereotype.Component;

import com.easy.location.dto.MyItemList;
import com.easy.location.dto.MyItem;
import com.easy.location.dto.Traveldto;

@Component
public class TravelAdapter {
    public static Traveldto toTraveldto(MyItemList myItemList) {
        return new Traveldto(
            myItemList.key(),
            null,
            myItemList.value().title(),
            myItemList.value().startDate(),
            myItemList.value().endDate(),
            myItemList.value().location(),
            myItemList.value().imageColor(),
            myItemList.value().statusText()
        );
    }

    public static Traveldto myItemToTraveldto(MyItem myItem) {
        System.out.println("myItem: " + myItem);
        return new Traveldto(
            myItem.plannerId(),
            null,
            myItem.title(),
            myItem.location(),
            myItem.startDate(),
            myItem.endDate(),
            myItem.imageColor(),
            myItem.statusText()
        );
    }

    public static MyItemList toMyItemList(Traveldto traveldto) {
        return new MyItemList(
            traveldto.id(),
            new MyItem(
                traveldto.image(),
                traveldto.title(),
                traveldto.startDate(),
                traveldto.endDate(),
                traveldto.location(),
                traveldto.status(),
                traveldto.id()
            )
        );
    }
}
