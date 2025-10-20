//package za.ac.cput.controller;
//
//import za.ac.cput.domain.Review;
//import za.ac.cput.service.IReviewService;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//class ReviewControllerTest {
//
//    private final IReviewService service = Mockito.mock(IReviewService.class);
//    private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new ReviewController(service)).build();
//
//    @Test
//    void createReview() throws Exception {
//        Review review = new Review("5", "Awesome");
//        when(service.create(Mockito.any(Review.class))).thenReturn(review);
//
//        mockMvc.perform(post("/reviews")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"rating\":\"5\",\"comment\":\"Awesome\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.rating").value("5"))
//                .andExpect(jsonPath("$.comment").value("Awesome"));
//    }
//}
//
