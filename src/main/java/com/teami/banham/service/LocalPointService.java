package com.teami.banham.service;

import com.teami.banham.dto.LocalPointDataDTO;
import com.teami.banham.entity.ProudBoardEntity;
import com.teami.banham.entity.localPointEntity.*;
import com.teami.banham.repository.localPointRepository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Service
@RequiredArgsConstructor
public class LocalPointService {
    private final FoodRepository foodRepository;
    private final HotelRepository hotelRepository;
    private final MedicalRepository medicalRepository;
    private final ServiceRepository serviceRepository;
    private final ShoppingRepository shoppingRepository;
    private final TravelRepository travelRepository;


    /* save 영역 시작 */

    public void foodDataSave(LocalPointDataDTO localPointDataDTO){
        FoodEntity foodEntity = FoodEntity.toSaveFoodEntity(localPointDataDTO);
        foodRepository.save(foodEntity);
    }

    public void hotelDataSave(LocalPointDataDTO localPointDataDTO){
        HotelEntity hotelEntity = HotelEntity.toSaveHotelEntity(localPointDataDTO);
        hotelRepository.save(hotelEntity);
    }

    public void medicalDataSave(LocalPointDataDTO localPointDataDTO){
        MedicalEntity medicalEntity = MedicalEntity.toSaveMedicalEntity(localPointDataDTO);
        medicalRepository.save(medicalEntity);
    }

    public void serviceDataSave(LocalPointDataDTO localPointDataDTO){
        ServiceEntity serviceEntity = ServiceEntity.toSaveServiceEntity(localPointDataDTO);
        serviceRepository.save(serviceEntity);
    }

    public void shoppingDataSave(LocalPointDataDTO localPointDataDTO){
        ShoppingEntity shoppingEntity = ShoppingEntity.toSaveShoppingEntity(localPointDataDTO);
        shoppingRepository.save(shoppingEntity);
    }

    public void travelDataSave(LocalPointDataDTO localPointDataDTO){
        TravelEntity travelEntity = TravelEntity.toSaveTravelEntity(localPointDataDTO);
        travelRepository.save(travelEntity);
    }

    /* save 영역 끝 */

    /* findAll 영역 시작 */

    @Cacheable(cacheNames ="foodList", cacheManager = "foodListCacheManager")
    public List<LocalPointDataDTO> foodDataFindAll(){
        List<FoodEntity> foodEntityList =foodRepository.findAll();
        List<LocalPointDataDTO> foodDTOList = new ArrayList<>();
        for(FoodEntity food : foodEntityList){
            foodDTOList.add(LocalPointDataDTO.toFoodDTO(food));
        }
        return foodDTOList;
    }

    @Cacheable(cacheNames ="hotelList", cacheManager = "hotelListCacheManager")
    public List<LocalPointDataDTO> hotelDataFindAll(){
        List<HotelEntity> hotelEntityList =hotelRepository.findAll();
        List<LocalPointDataDTO> hotelDTOList = new ArrayList<>();
        for(HotelEntity hotel : hotelEntityList){
            hotelDTOList.add(LocalPointDataDTO.toHotelDTO(hotel));
        }
        return hotelDTOList;
    }

    @Cacheable(cacheNames ="medicalList", cacheManager = "medicalListCacheManager")
    public List<LocalPointDataDTO> medicalDataFindAll(){
        List<MedicalEntity> medicalEntityList =medicalRepository.findAll();
        List<LocalPointDataDTO> medicalDTOList = new ArrayList<>();
        for(MedicalEntity medical : medicalEntityList){
            medicalDTOList.add(LocalPointDataDTO.toMedicalDTO(medical));
        }
        return medicalDTOList;
    }

    @Cacheable(cacheNames ="serviceList", cacheManager = "serviceListCacheManager")
    public List<LocalPointDataDTO> serviceDataFindAll(){
        List<ServiceEntity> serviceEntityList =serviceRepository.findAll();
        List<LocalPointDataDTO> serviceDTOList = new ArrayList<>();
        for(ServiceEntity service : serviceEntityList){
            serviceDTOList.add(LocalPointDataDTO.toServiceDTO(service));
        }
        return serviceDTOList;
    }

    @Cacheable(cacheNames ="shoppingList", cacheManager = "shoppingListCacheManager")
    public List<LocalPointDataDTO> shoppingDataFindAll(){
        List<ShoppingEntity> shoppingEntityList =shoppingRepository.findAll();
        List<LocalPointDataDTO> shoppingDTOList = new ArrayList<>();
        for(ShoppingEntity shopping : shoppingEntityList){
            shoppingDTOList.add(LocalPointDataDTO.toShoppingDTO(shopping));
        }
        return shoppingDTOList;
    }

    @Cacheable(cacheNames ="travelList", cacheManager = "travelListCacheManager")
    public List<LocalPointDataDTO> travelDataFindAll(){
        List<TravelEntity> travelEntityList =travelRepository.findAll();
        List<LocalPointDataDTO> travelDTOList = new ArrayList<>();
        for(TravelEntity travel : travelEntityList){
            travelDTOList.add(LocalPointDataDTO.toTravelDTO(travel));
        }
        return travelDTOList;
    }

    /* findAll 영역 끝 */


    /* deleteAll 영역 시작*/

    public void foodDataDeleteAll(){
        foodRepository.deleteAll();
    }

    public void hotelDataDeleteAll(){
        hotelRepository.deleteAll();
    }

    public void medicalDataDeleteAll(){
        medicalRepository.deleteAll();
    }

    public void serviceDataDeleteAll(){
        serviceRepository.deleteAll();
    }

    public void shoppingDataDeleteAll(){
        shoppingRepository.deleteAll();
    }

    public void travelDataDeleteAll(){
        travelRepository.deleteAll();
    }

    /* deleteAll 영역 끝*/
}
