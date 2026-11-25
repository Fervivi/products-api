/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.product.security;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cl.duoc.product.dto.response.ProductResponseDto;
import cl.duoc.product.service.ProductService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/** Verifica las reglas de acceso: catálogo público y escritura solo para el rol admin. */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SecurityConfigTest {

    private static final String VALID_PRODUCT = """
            {"nombre":"Producto","sku":"TS-001","descripcion":"Desc","caracteristicas":"Car",
             "imagen":"/img/pelota.png","precio":1000,"stock":1,"categoriaId":1,"estadoId":1}
            """;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.issuer}")
    private String issuer;

    private String tokenWithRole(String role) {
        return "Bearer "
                + JWT.create()
                        .withIssuer(issuer)
                        .withSubject("tester@duocuc.cl")
                        .withClaim("roles", List.of(role))
                        .withExpiresAt(Instant.now().plusSeconds(300))
                        .sign(Algorithm.HMAC256(secret));
    }

    @Test
    void getProducts_isPublic() throws Exception {
        when(productService.getActiveProducts()).thenReturn(List.of(new ProductResponseDto()));

        mockMvc.perform(get("/api/v1/products/active")).andExpect(status().isOk());
    }

    @Test
    void createProduct_withoutToken_returns401() throws Exception {
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_PRODUCT))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createProduct_withClientRole_returns403() throws Exception {
        mockMvc.perform(post("/api/v1/products")
                        .header("Authorization", tokenWithRole("user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_PRODUCT))
                .andExpect(status().isForbidden());
    }

    @Test
    void createProduct_withAdminRole_returns201() throws Exception {
        when(productService.createProduct(any())).thenReturn(new ProductResponseDto());

        mockMvc.perform(post("/api/v1/products")
                        .header("Authorization", tokenWithRole("admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_PRODUCT))
                .andExpect(status().isCreated());
    }

    @Test
    void deleteProduct_withClientRole_returns403() throws Exception {
        mockMvc.perform(delete("/api/v1/products/1").header("Authorization", tokenWithRole("user")))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteProduct_withAdminRole_returns200() throws Exception {
        when(productService.deactivateProduct(anyLong())).thenReturn(new ProductResponseDto());

        mockMvc.perform(delete("/api/v1/products/1").header("Authorization", tokenWithRole("admin")))
                .andExpect(status().isOk());
    }
}
