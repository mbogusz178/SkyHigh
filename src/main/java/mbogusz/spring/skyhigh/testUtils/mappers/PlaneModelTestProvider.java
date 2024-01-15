package mbogusz.spring.skyhigh.testUtils.mappers;

import mbogusz.spring.skyhigh.entity.PlaneModel;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class PlaneModelTestProvider implements Function<String, PlaneModel> {

    private static final Map<String, PlaneModel> PLANE_MODEL_SET = new HashMap<>();

    static {
        PlaneModel airbus = new PlaneModel();
        airbus.setId(0L);
        airbus.setManufacturer("Airbus");
        airbus.setFamily("A320");
        airbus.setModelNumber(200);
        airbus.setVersion(1);

        PLANE_MODEL_SET.put("sample_model", airbus);
    }

    @Override
    public PlaneModel apply(String value) {
        return PLANE_MODEL_SET.get(value);
    }
}
