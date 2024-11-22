package com.onedata.coding.test.controllerTest;

import com.onedata.coding.entity.Member;
import com.onedata.coding.service.serviceImpl.MemberServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberServiceImpl memberServiceImpl;


    @Test
    void testGetMemberById() throws Exception {
        Member member = new Member(1,"Name",9876543275L,LocalDate.now());
        when(memberServiceImpl.findById(1)).thenReturn(member);

        mockMvc.perform(get("/v1/api/member/id/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(member.getId()));
    }
    @Test
    void testDeleteMember() throws Exception {
        Member member = new Member(1,"Name",9876543275L,LocalDate.now());
        when(memberServiceImpl.deleteById(1)).thenReturn("Member with ID " + 1 + " deleted successfully.");

        mockMvc.perform(delete("/v1/api/member/{id}",1)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("Member with ID " + 1 + " deleted successfully."));
    }
}
