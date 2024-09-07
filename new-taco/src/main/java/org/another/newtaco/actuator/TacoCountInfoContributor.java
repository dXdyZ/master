package org.another.newtaco.actuator;

import lombok.RequiredArgsConstructor;
import org.another.newtaco.repository.TacoRepository;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TacoCountInfoContributor implements InfoContributor {
    private final TacoRepository tacoRepository;

    @Override
    public void contribute(Info.Builder builder) {
        long tacoCount = tacoRepository.count();
        Map<String, Object> tacoMap = new HashMap<>();
        tacoMap.put("count", tacoCount);
        builder.withDetail("taco-stats", tacoMap);
    }
}




























