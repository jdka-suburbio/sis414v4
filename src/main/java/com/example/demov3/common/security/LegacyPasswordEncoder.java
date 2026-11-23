package com.example.demov3.common.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Reproduce ENCRIPTA / DESENCRIPTA de vSIAFCode/passw.spr para que PASSW.PSW siga siendo
 * compatible con el sistema Visual FoxPro. No es un hash: es cifrado reversible.
 * <p>
 * Internamente trabaja con "cadenas de bytes" (cada char = un byte 0..255, como CHR/ASC de VFP).
 * El valor guardado se convierte desde/hacia windows-1252, la pagina de codigos de los DBF.
 */
public class LegacyPasswordEncoder implements PasswordEncoder {

    private static final Charset CP1252 = Charset.forName("windows-1252");

    @Override
    public String encode(CharSequence rawPassword) {
        return toStorage(encripta(allTrim(rawPassword.toString())));
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword == null || encodedPassword == null) {
            return false;
        }
        String clave = allTrim(rawPassword.toString());
        if (clave.isEmpty()) {
            return false;
        }
        try {
            // Legacy: ALLTRIM(passw.psw) = ALLTRIM(encripta(ALLTRIM(m.psw)))
            byte[] esperado = allTrim(encripta(clave)).getBytes(StandardCharsets.ISO_8859_1);
            byte[] guardado = allTrim(fromStorage(encodedPassword)).getBytes(StandardCharsets.ISO_8859_1);
            return MessageDigest.isEqual(esperado, guardado);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public String decode(String encodedPassword) {
        return toStorage(desencripta(allTrim(fromStorage(encodedPassword))));
    }

    // PROCEDURE encripta
    static String encripta(String cadena) {
        String acum1 = "";
        for (int i = 1; i <= cadena.length(); i++) {
            int b = asc(cadena.charAt(i - 1)) + (i % 2 == 1 ? 1 : -1);
            acum1 = allTrim(acum1) + chr(b);
        }
        String base = allTrim(acum1);
        String acum = "";
        for (int c = 1; c <= base.length(); c++) {
            acum = allTrim(acum) + chr(asc(base.charAt(c - 1)) + 36 + c);
        }
        return acum;
    }

    // PROCEDURE desencripta
    static String desencripta(String cadena) {
        String acum1 = "";
        for (int i = 1; i <= cadena.length(); i++) {
            int b = asc(cadena.charAt(i - 1)) + (i % 2 == 0 ? 1 : -1);
            acum1 = allTrim(acum1) + chr(b);
        }
        String base = allTrim(acum1);
        String acum = "";
        for (int c = 1; c <= base.length(); c++) {
            acum = allTrim(acum) + chr(asc(base.charAt(c - 1)) - (36 + c));
        }
        return acum;
    }

    // ALLTRIM de VFP: solo recorta espacios
    private static String allTrim(String s) {
        int ini = 0;
        int fin = s.length();
        while (ini < fin && s.charAt(ini) == ' ') {
            ini++;
        }
        while (fin > ini && s.charAt(fin - 1) == ' ') {
            fin--;
        }
        return s.substring(ini, fin);
    }

    // CHR() de VFP: falla fuera de 0..255
    private static char chr(int b) {
        if (b < 0 || b > 255) {
            throw new IllegalArgumentException("CHR fuera de rango: " + b);
        }
        return (char) b;
    }

    // ASC() sobre la cadena de bytes
    private static int asc(char c) {
        if (c > 0xFF) {
            throw new IllegalArgumentException("Caracter no representable: " + (int) c);
        }
        return c;
    }

    // valor guardado (texto) -> cadena de bytes. Acepta windows-1252 y latin1 (0x80..0x9F directos)
    private static String fromStorage(String stored) {
        StringBuilder sb = new StringBuilder(stored.length());
        for (char c : stored.toCharArray()) {
            if (c <= 0xFF) {
                sb.append(c);
            } else if (CP1252.newEncoder().canEncode(c)) {
                sb.append((char) (String.valueOf(c).getBytes(CP1252)[0] & 0xFF));
            } else {
                throw new IllegalArgumentException("Caracter no representable: " + (int) c);
            }
        }
        return sb.toString();
    }

    // cadena de bytes -> texto a guardar (windows-1252, como lo leeria una importacion del DBF)
    private static String toStorage(String bytes) {
        StringBuilder sb = new StringBuilder(bytes.length());
        for (char b : bytes.toCharArray()) {
            char c = new String(new byte[]{(byte) b}, CP1252).charAt(0);
            sb.append(c == '�' ? b : c);
        }
        return sb.toString();
    }
}
