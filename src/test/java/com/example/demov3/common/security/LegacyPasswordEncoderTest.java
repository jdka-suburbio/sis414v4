package com.example.demov3.common.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LegacyPasswordEncoderTest {

    private final LegacyPasswordEncoder encoder = new LegacyPasswordEncoder();

    // PSW real de passw.dbf (usuario admin)
    @Test
    void coincideConValorGuardadoEnPasswDbf() {
        assertEquals("WW[[", encoder.encode("1234"));
        assertTrue(encoder.matches("1234", "WW[["));
        assertTrue(encoder.matches("1234", "WW[[      "));
    }

    @Test
    void rechazaClaveIncorrectaOVacia() {
        assertFalse(encoder.matches("1235", "WW[["));
        assertFalse(encoder.matches("", "WW[["));
        assertFalse(encoder.matches("1234", ""));
    }

    @Test
    void desencriptaIgualQueLegacy() {
        assertEquals("1234", encoder.decode("WW[["));
        assertEquals("admin", encoder.decode(encoder.encode("admin")));
    }

    @Test
    void aceptaValorGuardadoComoWindows1252OLatin1() {
        // encripta('admin') = bytes 87 89 95 90 98
        assertTrue(encoder.matches("admin", "\u0087\u0089\u0095\u0090\u0098"));
        assertTrue(encoder.matches("admin", encoder.encode("admin")));
    }

    @Test
    void claveLargaNoRompeElCodificador() {
        // Spring Security codifica una clave ficticia de 20 caracteres contra ataques de timing
        assertDoesNotThrow(() -> encoder.encode("userNotFoundPassword"));
    }
}
