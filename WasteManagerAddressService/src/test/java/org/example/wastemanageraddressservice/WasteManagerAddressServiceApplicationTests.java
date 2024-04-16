package org.example.wastemanageraddressservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.example.wastemanageraddressservice.Controller.Exception.BDExcepcion.NotExistException;
import org.example.wastemanageraddressservice.Entity.WasteManagerAddressEntity;
import org.example.wastemanageraddressservice.Service.Contract.WasteManagerAddressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "spring.config.name=test")
@AutoConfigureMockMvc
class WasteManagerAddressServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EntityManager entityManager;

    @MockBean
    private WasteManagerAddressService wasteManagerAddressService;

    private List<WasteManagerAddressEntity> setup(){
        WasteManagerAddressEntity wm = new WasteManagerAddressEntity();
        wm.setAddress("address");
        wm.setVersion(2);
        wm.setCreatedDate(new Date());
        wm.setLastModifiedDate(new Date());
        wm.setId(1L);

        WasteManagerAddressEntity wm1 = new WasteManagerAddressEntity();
        wm.setAddress("address2");
        wm.setVersion(2);
        wm.setCreatedDate(new Date());
        wm.setLastModifiedDate(new Date());
        wm.setId(2L);

        return List.of(wm, wm1);
    }

    @Test
    public void call_findAll_result_200_OK() throws Exception {
        when(wasteManagerAddressService.findAll()).thenReturn(setup());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/wasteManagerAddress")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
    @Test
    public void call_findAll_ListEmpty_result_400_BAD_REQUEST() throws Exception {
        when(wasteManagerAddressService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/wasteManagerAddress")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("ListEmpty"));
    }

    @Test
    public void call_findById_result_200_OK() throws Exception {
        long id = 1;
        when(wasteManagerAddressService.findById(id)).thenReturn(setup().get(0));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/wasteManagerAddress/findById/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void call_findById_invalid_argument_result_400_BAD_REQUEST() throws Exception {
        when(wasteManagerAddressService.findById(anyLong())).thenReturn(setup().get(0));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/wasteManagerAddress/findById/{id}", "anyString")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.type").value("InvalidArgument"));
    }

    @Test
    public void call_findById_without_argument_result_500_INTERNAL_SERVER_ERROR() throws Exception {
        when(wasteManagerAddressService.findById(anyLong())).thenReturn(setup().get(0));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/wasteManagerAddress/findById/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.type").value("Anomaly"));
    }

    @Test
    public void call_findById_not_exist_result_404_NOT_FOUND() throws Exception {
        when(wasteManagerAddressService.findById(0)).thenThrow(new NotExistException(0L,"id"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/wasteManagerAddress/findById/", 0)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.type").value("Anomaly"));
    }

    @Test
    @Transactional
    public void call_create_result_201_CREATED() throws Exception {
        WasteManagerAddressEntity wm = new WasteManagerAddressEntity();
        wm.setEnabled(true);
        wm.setVersion(5);
        wm.setAddress("address");

        entityManager.persist(setup().get(1));

        WasteManagerAddressEntity entity = entityManager.find(WasteManagerAddressEntity.class, 1L);

        when(wasteManagerAddressService.save(any(WasteManagerAddressEntity.class))).thenReturn(entity);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(wm);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/wasteManagerAddress/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        boolean isNumeric = content.chars().allMatch(Character::isDigit);
        assertTrue(isNumeric);
    }

    @Test
    public void call_create_invalid_attribute_result_409_CONFLICT() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/wasteManagerAddress/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"address": "123 Main St",
                                    "isEnabled": "azul",
                                    "version": 1
                                  }"""))
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void call_create_empty_body_result_400_BAD_REQUEST() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/wasteManagerAddress/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @Transactional
    public void call_update_result_200_OK() throws Exception {
        WasteManagerAddressEntity wm = new WasteManagerAddressEntity();
        wm.setEnabled(true);
        wm.setVersion(5);
        wm.setAddress("address");

        entityManager.persist(setup().get(1));

        WasteManagerAddressEntity entity = entityManager.find(WasteManagerAddressEntity.class, 1L);

        when(wasteManagerAddressService.findById(anyLong())).thenReturn(entity);
        when(wasteManagerAddressService.save(any(WasteManagerAddressEntity.class))).thenReturn(entity);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(wm);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/wasteManagerAddress/update/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.createdDate").exists())
                .andExpect(jsonPath("$.lastModifiedDate").exists());

    }

    @Test
    public void call_delete_result_200_OK() throws Exception {
        long idToDelete = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/wasteManagerAddress/delete/{id}", idToDelete))
                .andExpect(status().isOk());
    }
}
