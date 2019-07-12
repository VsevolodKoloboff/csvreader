import config.RootTestConfig;
import net.coloboff.csvreader.core.CsvReader;
import net.coloboff.csvreader.core.dto.AirportDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootTestConfig.class})
public class CsvReaderTest {

    @Autowired
    private CsvReader csvReader;

    @Test
    public void testReadAll() throws IOException {
        List<AirportDTO> airportDTOList = (List<AirportDTO>) csvReader.getAll();
        assertTrue(!airportDTOList.isEmpty());
        assertEquals(airportDTOList.size(), 7184);
    }
    @Test
    public void testFilter() throws  IOException {
        List<AirportDTO> airportDTOS = (List<AirportDTO>) csvReader.filter("Canada");
        assertTrue(!airportDTOS.isEmpty());
        assertEquals(airportDTOS.size(), 417);
        AirportDTO airportDTO = airportDTOS.get(416);
        assertEquals(airportDTO.getFields().get(0), "11784");
        assertEquals(airportDTO.getFields().get(1), "Ignace Municipal Airport");
        assertEquals(airportDTO.getFields().get(2), "Ignace");
        assertEquals(airportDTO.getFields().get(3), "Canada");
        assertEquals(airportDTO.getFields().get(4), "ZUC");
        assertEquals(airportDTO.getFields().get(5), "CZUC");
        assertEquals(airportDTO.getFields().get(6), "49.4296989440918");
        assertEquals(airportDTO.getFields().get(7), "-91.7177963256836");
        assertEquals(airportDTO.getFields().get(8), "1435");
        assertEquals(airportDTO.getFields().get(9), "\\N");
        assertEquals(airportDTO.getFields().get(10), "\\N");
        assertEquals(airportDTO.getFields().get(11), "\\N");
        assertEquals(airportDTO.getFields().get(12), "airport");
        assertEquals(airportDTO.getFields().get(13), "OurAirports");

    }

}
