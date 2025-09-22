package com.example.demo.service;
import com.example.demo.model.Buses;
import com.example.demo.repository.BusRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;

@Service
public class BusService {

    private static final Logger logger = LoggerFactory.getLogger(BusService.class);

    @Autowired
    private BusRepository busRepository;


    /**
     * Get all buses
     * @return List of all buses
     */
    public List<Buses> getAllBuses() {
        logger.debug("Fetching all buses");
        return busRepository.findAll();
    }

    /**
     * Get bus by ID
     * @param id Bus ID
     * @return Optional containing the bus if found
     */
    public Optional<Buses> getBusById(String id) {
        logger.debug("Fetching bus with ID: {}", id);
        if (id == null || id.trim().isEmpty()) {
            logger.warn("Invalid bus ID provided: {}", id);
            return Optional.empty();
        }
        return busRepository.findById(id);
    }

    /**
     * Get bus by bus number
     * @param busNo Bus number
     * @return Bus if found, null otherwise
     */
    public Buses getBusByBusNo(String busNo) {
        logger.debug("Fetching bus with busNo: {}", busNo);
        if (busNo == null || busNo.trim().isEmpty()) {
            logger.warn("Invalid bus number provided: {}", busNo);
            return null;
        }
        return busRepository.findByBusNo(busNo);
    }

    /**
     * Get bus by bus number (safer version returning Optional)
     * @param busNo Bus number
     * @return Optional containing the bus if found
     */
    public Optional<Buses> getBusByBusNoOptional(String busNo) {
        logger.debug("Fetching bus with busNo (Optional): {}", busNo);
        if (busNo == null || busNo.trim().isEmpty()) {
            logger.warn("Invalid bus number provided: {}", busNo);
            return Optional.empty();
        }
        return busRepository.findOptionalByBusNo(busNo);
    }

    /**
     * Get bus by bus plate
     * @param busPlate Bus plate
     * @return Optional containing the bus if found
     */
    public Optional<Buses> getBusByBusPlate(String busPlate) {
        logger.debug("Fetching bus with busPlate: {}", busPlate);
        if (busPlate == null || busPlate.trim().isEmpty()) {
            logger.warn("Invalid bus plate provided: {}", busPlate);
            return Optional.empty();
        }
        return busRepository.findByBusPlate(busPlate);
    }

    /**
     * Save a bus (create or update)
     * @param bus Bus to save
    * @return Saved bus
//     * @throws DuplicateKeyException if bus number or plate already exists
//     */
  public Buses saveBus(Buses bus) throws DuplicateKeyException {
      logger.debug("Saving bus: {}", bus);

      if (bus == null) {
           throw new IllegalArgumentException("Bus cannot be null");
       }

//     // Validate required fields
        if (bus.getbusNo() == null || bus.getbusNo().trim().isEmpty()) {
           throw new IllegalArgumentException("Bus number is required");
      }

      if (bus.getDriverName() == null || bus.getDriverName().trim().isEmpty()) {
           throw new IllegalArgumentException("Driver name is required");
     }
//
     try {
//            // Check for duplicates only when creating new bus (no ID)
           if (bus.getId() == null) {
              if (busRepository.existsByBusNo(bus.getbusNo())) {
                  throw new DuplicateKeyException("Bus number already exists: " + bus.getbusNo());
               }

                if (bus.getBusPlate() != null && !bus.getBusPlate().trim().isEmpty()
                      && busRepository.existsByBusPlate(bus.getBusPlate())) {
                  throw new DuplicateKeyException("Bus plate already exists: " + bus.getBusPlate());
              }
          }



         logger.info("Successfully saved bus with ID: {}", bus.getId());
            return bus;

       } catch (org.springframework.dao.DuplicateKeyException e) {
          logger.error("Duplicate key error saving bus: {}", e.getMessage());
           throw e;
       } catch (Exception e) {
           logger.error("Error saving bus: {}", e.getMessage(), e);
          throw e;
      }
    }

  /**
     * Update an existing bus
     * @param id Bus Event.ID
     * @param updatedBus Updated bus data
     * @return Updated bus
     * @throws IllegalArgumentException if bus doesn't exist
**/

   public Buses updateBus(String id, Buses updatedBus) {
       logger.debug("Updating bus with ID: {}", id);

       Optional<Buses> existingBusOpt = getBusById(id);
       if (existingBusOpt.isEmpty()) {
          throw new IllegalArgumentException("Bus not found with ID: " + id);
   }
       updatedBus.setbusNo(updatedBus.getbusNo());
        return saveBus(updatedBus);
   }

//    /**
//     * Delete a bus by ID
//     * @param id Bus ID
//     * @return true if deleted, false if not found
//     */
   public void deleteBus(String id) {
        logger.debug("Deleting bus with ID: {}", id);

       if (id == null || id.trim().isEmpty()) {
            logger.warn("Invalid bus ID provided for deletion: {}", id);
           return;
        }

       if (busRepository.existsById(id)) {
          busRepository.deleteById(id);
          logger.info("Successfully deleted bus with ID: {}", id);
       } else {
          logger.warn("Bus not found for deletion with ID: {}", id);
       }
   }
//
//    /**
//     * Check if bus exists by ID
//     * @param id Bus ID
//     * @return true if exists, false otherwise
//     */
   public boolean existsById(String id) {
        if (id == null || id.trim().isEmpty()) {
           return false;
       }
       return busRepository.existsById(id);
    }
//
//    /**
//     * Check if bus number exists
//     * @param busNo Bus number
//     * @return true if exists, false otherwise
//     */
 public boolean existsByBusNo(String busNo) {
  if (busNo == null || busNo.trim().isEmpty()) {
           return false;
      }
       return busRepository.existsByBusNo(busNo);
   }

//    /**
//     * Check if bus plate exists
//     * @param busPlate Bus plate
//     * @return true if exists, false otherwise
//     */
   public boolean existsByBusPlate(String busPlate) {
      if (busPlate == null || busPlate.trim().isEmpty()) {
          return false;
      }
       return busRepository.existsByBusPlate(busPlate);
   }

    /** Get total count of buses

   @return Total number of buses
     *
     */
   public long getTotalBusCount() {
       return busRepository.count();
  }
}