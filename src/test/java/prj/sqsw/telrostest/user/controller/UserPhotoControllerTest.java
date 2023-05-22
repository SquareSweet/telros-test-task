package prj.sqsw.telrostest.user.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import prj.sqsw.telrostest.user.service.UserPhotoService;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserPhotoController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserPhotoControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserPhotoService mockService;

    @Test
    void downloadPhotoTest() throws Exception {
        File imageFile = new File("src/test/resources", "photo.png");
        try (InputStream inputStream = new FileInputStream(imageFile)) {
            when(mockService.getPhoto(1L)).thenReturn(inputStream.readAllBytes());
            mvc.perform(get("/api/users/1/photo"))
                    .andExpect(status().is(200))
                    .andExpect(content().bytes(Files.readAllBytes(imageFile.toPath())));
            verify(mockService, times(1)).getPhoto(1L);
        }
    }

    @Test
    void uploadPhotoTest() throws Exception {
        File imageFile = new File("src/test/resources", "photo.png");
        try (InputStream inputStream = new FileInputStream(imageFile)) {
            MockMultipartFile photo = new MockMultipartFile("photo", "photo.jpg", "application/json", inputStream.readAllBytes());
            mvc.perform(multipart("/api/users/1/photo")
                            .file(photo))
                    .andExpect(status().is(200));
            verify(mockService, times(1)).uploadPhoto(1L, photo, "profile_photos");
        }
    }

    @Test
    void deletePhotoTest() throws Exception {
        mvc.perform(delete("/api/users/1/photo"))
                .andExpect(status().is(200));
        verify(mockService, times(1)).deletePhoto(1L);
    }
}