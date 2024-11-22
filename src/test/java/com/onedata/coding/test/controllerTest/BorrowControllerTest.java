package com.onedata.coding.test.controllerTest;

import com.onedata.coding.entity.Borrow;
import com.onedata.coding.service.serviceImpl.BorrowServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BorrowControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowServiceImpl borrowServiceImpl;

    @Test
    void testGetById() throws Exception {
        Borrow borrow = new Borrow(1,2,3,null,null);
        when(borrowServiceImpl.findById(1)).thenReturn(borrow);

        mockMvc.perform(get("/v1/api/borrow/id/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(borrow.getId()));
    }
    @Test
    void testDeleteBorrow() throws Exception {
        Borrow borrow = new Borrow(1,2,3,null,null);
        when(borrowServiceImpl.deleteBorrowedRecord(1)).thenReturn("Borrow with ID " + 1 + " deleted successfully.");

        mockMvc.perform(delete("/v1/api/borrow/{id}",1)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("Borrow with ID " + 1 + " deleted successfully."));
    }
}
