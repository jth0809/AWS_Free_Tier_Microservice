package com.easy.location.Adapter;

import org.springframework.stereotype.Component;

import com.easy.location.domain.Location;
import com.easy.location.domain.TravelLocation;
import com.easy.location.dto.PlanList;
import com.easy.location.dto.Plandto;
import com.easy.location.dto.PlannerItem;


@Component
public class TravelPlanAdapter {
    public static Plandto toPlandto(PlanList planList) {
        Location location = new Location(
            planList.value().latitude(),
            planList.value().longitude()
        );
        TravelLocation travelLocation = new TravelLocation(
            null,
            null,
            location,
            null,
            null,
            null
        );
        return new Plandto(
            planList.key(),
            null,
            planList.value().title(),
            planList.value().memo(),
            planList.value().startTime(),
            planList.value().endTime(),
            planList.value().location(),
            travelLocation
        );

    }

    public static PlanList toPlanList(Plandto plandto) {
        return new PlanList(
            plandto.id(),
            new PlannerItem(
                plandto.title(),
                plandto.startDate(),
                plandto.endDate(),
                plandto.image(),
                plandto.description(),
                plandto.travelPlanLocation().getTravelLocation().getLatitude(),
                plandto.travelPlanLocation().getTravelLocation().getLongitude()
            )
        );
    }
}
