package mbogusz.spring.skyhigh.db;

import mbogusz.spring.skyhigh.SkyHighApplication;
import mbogusz.spring.skyhigh.entity.Plane;
import mbogusz.spring.skyhigh.entity.dto.PlaneDTO;
import mbogusz.spring.skyhigh.mapper.EntityMapper;
import mbogusz.spring.skyhigh.mapper.PlaneMapper;
import mbogusz.spring.skyhigh.repository.PlaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SkyHighApplication.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class GetAllPlanesIT extends GetAllIT<String, Plane, PlaneDTO> {
    private PlaneRepository planeRepository;
    private PlaneMapper planeMapper;

    @Override
    protected JpaRepository<Plane, String> getRepository() {
        return planeRepository;
    }

    @Override
    protected EntityMapper<String, Plane, PlaneDTO> getMapper() {
        return planeMapper;
    }

    @Override
    protected String getControllerURL() {
        return "/api/planes";
    }

    @Override
    protected Class<Plane> getType() {
        return Plane.class;
    }

    @Override
    protected Class<PlaneDTO> getDtoType() {
        return PlaneDTO.class;
    }

    @Autowired
    public void setPlaneRepository(PlaneRepository planeRepository) {
        this.planeRepository = planeRepository;
    }

    @Autowired
    public void setPlaneMapper(PlaneMapper planeMapper) {
        this.planeMapper = planeMapper;
    }

//    private SeatConfiguration getTestSeatConfig() {
//        SeatConfiguration seatConfiguration = new SeatConfiguration();
//        seatConfiguration.setRowConfig("3-3");
//        seatConfiguration.setNumRows(2);
//        return seatConfiguration;
//    }
//
//    private PlaneModel getTestPlaneModel() {
//        PlaneModel airbus = new PlaneModel();
//        airbus.setManufacturer("Airbus");
//        airbus.setFamily("A320");
//        airbus.setModelNumber(200);
//        airbus.setVersion(1);
//        return airbus;
//    }
//
//    private Plane createTestPlane() {
//        PlaneModel airbus = getTestPlaneModel();
//        SeatConfiguration seatConfig = getTestSeatConfig();
//        Plane plane = new Plane("SKY-AA", airbus, seatConfig);
//        seatConfigurationRepository.save(seatConfig);
//        planeModelRepository.save(airbus);
//        return planeRepository.save(plane);
//    }
}
