package org.example.waste_manager_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.example.waste_manager_service.Controller.Exception.BDExcepcion.NotExistException;
import org.example.waste_manager_service.Entity.WasteManagerAddressEntity;
import org.example.waste_manager_service.Entity.WasteManagerEntity;
import org.example.waste_manager_service.Service.Contract.WasteManagerAddressClient;
import org.example.waste_manager_service.Service.Contract.WasteManagerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "spring.config.name=test")
@AutoConfigureMockMvc
@ActiveProfiles("test")
class WasteManagerServiceApplicationTests {

    @MockBean
    private WasteManagerService wasteManagerService;

    @MockBean
    private WasteManagerAddressClient wasteManagerAddressClient;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void call_findAll_200_OK() throws Exception {
        WasteManagerEntity wasteManagerEntity = getWasteManagerEntity();
        WasteManagerAddressEntity wasteManagerAddressEntity = getWasteManagerAddress();

        when(wasteManagerService.findAll()).thenReturn(Collections.singletonList(wasteManagerEntity));
        when(wasteManagerAddressClient.findAll()).thenReturn(Collections.singletonList(wasteManagerAddressEntity));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/wasteManager"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertFalse(result.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void call_findById_200_OK() throws Exception {
        long id = 1L;
        WasteManagerEntity wasteManagerEntity = getWasteManagerEntity();
        WasteManagerAddressEntity wasteManagerAddressEntity = getWasteManagerAddress();

        when(wasteManagerService.findById(id)).thenReturn(wasteManagerEntity);
        when(wasteManagerAddressClient.findById(id)).thenReturn(wasteManagerAddressEntity);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/wasteManager/findById/{id}", id))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertFalse(result.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void call_findById_invalid_argument_result_400_BAD_REQUEST() throws Exception {
        when(wasteManagerService.findById(anyLong())).thenReturn(getWasteManagerEntity());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/wasteManager/findById/{id}", "anyString")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.type").value("InvalidArgument"));
    }

    @Test
    public void call_findById_without_argument_result_500_INTERNAL_SERVER_ERROR() throws Exception {
        when(wasteManagerService.findById(anyLong())).thenReturn(new WasteManagerEntity());
        when(wasteManagerAddressClient.findById(anyLong())).thenReturn(getWasteManagerAddress());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/wasteManager/findById/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.type").value("Anomaly"));
    }

    @Test
    public void call_findById_not_exist_result_500_INTERNAL_SERVER_ERROR() throws Exception {
        when(wasteManagerService.findById(0)).thenThrow(new NotExistException(0L,"id"));
        when(wasteManagerAddressClient.findById(anyLong())).thenReturn(getWasteManagerAddress());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/wasteManager/findById/", 0)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.type").value("Anomaly"));
    }

    @Test
    @Transactional
    public void call_create_result_201_CREATED() throws Exception {
        WasteManagerAddressEntity wasteManagerAddress = getWasteManagerAddress();
        WasteManagerEntity wasteManagerEntity = getWasteManagerEntity();

        entityManager.persist(wasteManagerEntity);

        when(wasteManagerService.save(any(WasteManagerEntity.class))).thenReturn(wasteManagerEntity);
        when(wasteManagerAddressClient.findById(anyLong())).thenReturn(wasteManagerAddress);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(wasteManagerEntity);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/wasteManager/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.createDate").exists())
                .andExpect(jsonPath("$.lastModifiedDate").exists());

    }

    private static WasteManagerEntity getWasteManagerEntity() {
        WasteManagerEntity wasteManagerEntity = new WasteManagerEntity();
        wasteManagerEntity.setEnabled(true);
        wasteManagerEntity.setNif("aninif");
        wasteManagerEntity.setName("anyName");
        wasteManagerEntity.setIdWasteManagerAddressEntity(1);
        wasteManagerEntity.setVersion(8);
        return wasteManagerEntity;
    }

    private static WasteManagerAddressEntity getWasteManagerAddress() {
        return new WasteManagerAddressEntity(1L, "address", true, 10L, null, null);
    }


    @Test
    public void call_create_empty_body_result_400_BAD_REQUEST() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/wasteManager/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @Transactional
    public void call_update_result_200_OK() throws Exception {
        WasteManagerAddressEntity wasteManagerAddress = getWasteManagerAddress();
        WasteManagerEntity wasteManagerEntity = getWasteManagerEntity();

        WasteManagerEntity wasteManager = getWasteManagerEntity();
        wasteManager.setWasteManagerAddressEntity(wasteManagerAddress);
        wasteManager.setCreateDate(new Date());
        wasteManager.setLastModifiedDate(new Date());
        wasteManager.setId(1L);

        entityManager.persist(wasteManagerEntity);

        WasteManagerEntity entity = entityManager.find(WasteManagerEntity.class, 1L);

        when(wasteManagerService.findById(anyLong())).thenReturn(entity);
        when(wasteManagerService.save(any(WasteManagerEntity.class))).thenReturn(wasteManager);
        when(wasteManagerAddressClient.findById(anyLong())).thenReturn(wasteManagerAddress);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(wasteManager);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/wasteManager/update/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.createDate").exists())
                .andExpect(jsonPath("$.lastModifiedDate").exists());

    }

    @Test
    public void call_delete_result_200_OK() throws Exception {
        long idToDelete = 1L;
        when(wasteManagerService.findById(anyLong())).thenReturn(getWasteManagerEntity());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/wasteManager/delete/{id}", idToDelete))
                .andExpect(status().isOk());
    }

}
