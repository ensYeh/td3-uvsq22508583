package fr.uvsq.cprog.collex;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

class DnsTest {

    private Dns dns;
    private static final Path TEST_FILE = Path.of("dns_database_runtime.txt");

    @BeforeEach
    void setUp() throws IOException {
        // Crée un fichier vide pour chaque test pour ne pas polluer la vraie base
        if (Files.exists(TEST_FILE)) {
            Files.delete(TEST_FILE);
        }
        Files.createFile(TEST_FILE);
        dns = new Dns();
    }

    @AfterEach
    void tearDown() throws IOException {
        // Supprime le fichier test après chaque test
        if (Files.exists(TEST_FILE)) {
            Files.delete(TEST_FILE);
        }
    }

    @Test
    void testAddItemSuccess() {
        AdresseIP ip = new AdresseIP(192, 168, 1, 10);
        NomMachine nom = new NomMachine("pc1", "uvsq", "fr");

        String result = dns.addItem(ip, nom);
        assertEquals("Entrée ajoutée avec succès  ", result);

        DnsItem item = dns.getItem(ip);
        assertNotNull(item.getNomMac());
        assertEquals(nom.toString(), item.getNomMac().toString());
    }

    @Test
    void testAddItemDuplicateIP() {
        AdresseIP ip = new AdresseIP(192, 168, 1, 11);
        NomMachine nom1 = new NomMachine("pc2", "uvsq", "fr");
        NomMachine nom2 = new NomMachine("pc3", "uvsq", "fr");

        dns.addItem(ip, nom1);
        String result = dns.addItem(ip, nom2);

        assertTrue(result.contains("Erreur"));
    }

    @Test
    void testAddItemDuplicateNom() {
        AdresseIP ip1 = new AdresseIP(192, 168, 1, 12);
        AdresseIP ip2 = new AdresseIP(192, 168, 1, 13);
        NomMachine nom = new NomMachine("pc4", "uvsq", "fr");

        dns.addItem(ip1, nom);
        String result = dns.addItem(ip2, nom);

        assertTrue(result.contains("Erreur"));
    }

    @Test
    void testGetItemByIPNotFound() {
        AdresseIP ip = new AdresseIP(10, 0, 0, 1);
        DnsItem item = dns.getItem(ip);
        assertNull(item.getNomMac());
    }

    @Test
    void testGetItemsByDomain() {
        AdresseIP ip1 = new AdresseIP(10, 0, 0, 2);
        AdresseIP ip2 = new AdresseIP(10, 0, 0, 3);
        NomMachine nom1 = new NomMachine("pc1", "uvsq", "fr");
        NomMachine nom2 = new NomMachine("pc2", "uvsq", "fr");

        dns.addItem(ip1, nom1);
        dns.addItem(ip2, nom2);

        List<DnsItem> list = dns.getItems("uvsq.fr");
        assertEquals(2, list.size());
    }
}
