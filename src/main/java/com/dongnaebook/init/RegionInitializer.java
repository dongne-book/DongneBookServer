package com.dongnaebook.init;

import com.dongnaebook.domain.region.Region;
import com.dongnaebook.domain.region.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RegionInitializer implements ApplicationRunner {

    private final RegionRepository regionRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        File file = new File("src/main/resources/regions-utf8.txt"); // 경로는 필요에 따라 조정

        List<Region> regions = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");

                String code = parts[0];
                String name = parts[1];
                String status = parts[2];

                System.out.println("Processing region: " + code + " - " + name + " - " + status);


                if (!"존재".equals(status)) continue; // 폐지된 동 제외

                Region region = Region.builder()
                        .code(code)
                        .name(name)
                        .build();

                regions.add(region);
            }
        }

        regionRepository.deleteAllInBatch();
        regionRepository.saveAll(regions);
    }
}