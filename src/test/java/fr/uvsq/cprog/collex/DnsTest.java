package fr.uvsq.cprog.collex;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.*;
import java.util.List;

class DnsTest {

    private Dns dns;
    private static final Path TEST_FILE = Path.of("dns_database_runtime.txt");
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() throws IOException {
        // Capture la sortie console (utile pour RechercherMachinesDomaine)
        System.setOut(new PrintStream(outContent));

        // Crée une base DNS temporaire
        if (Files.exists(TEST_FILE)) Files.delete(TEST_FILE);
        Files.createFile(TEST_FILE);
        dns = new Dns(TEST_FILE);
    }

    @AfterEach
    void tearDown() throws IOException {
        System.setOut(originalOut);
        if (Files.exists(TEST_FILE)) Files.delete(TEST_FILE);
    }

    //  Dns 

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
    void testGetItemsByDomain() {
        dns.addItem(new AdresseIP(10, 0, 0, 2), new NomMachine("pc1", "uvsq", "fr"));
        dns.addItem(new AdresseIP(10, 0, 0, 3), new NomMachine("pc2", "uvsq", "fr"));

        List<DnsItem> list = dns.getItems("uvsq.fr");
        assertEquals(2, list.size());
    }

    @Test
    void testRechercheSansDomaine() {
        new RechercherMachinesDomaine("ls");
        String output = outContent.toString();
        assertTrue(output.contains("Erreur : nom de domaine manquant"));
    }


}
