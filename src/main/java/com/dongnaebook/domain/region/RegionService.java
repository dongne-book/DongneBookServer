package com.dongnaebook.domain.region;

import com.dongnaebook.common.exception.NotFoundException;
import com.dongnaebook.domain.region.DTO.RegionResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegionService {
    private final RegionRepository regionRepository;

    public RegionResponseDTO checkRegion(String regionCode) {
        return RegionMapper.toResponseDTO(regionRepository.getByCode(regionCode).orElseThrow(() -> new NotFoundException("없는 지역입니다.")));
    }
    public List<RegionResponseDTO> findByRegionName(String regionName) {
        return regionRepository.findBestMatchingRegion(regionName).stream().map(RegionMapper::toResponseDTO).toList();
    }
}
