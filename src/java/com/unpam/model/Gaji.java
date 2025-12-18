/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unpam.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.unpam.view.PesanDialog;
import java.io.ByteArrayOutputStream;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;

/**
 *
 * @author Verrel
 */
public class Gaji {

   private String ktp;
    private String kodePekerjaan;
    private String gajiBersih;
    private String gajiKotor;
    private String tunjangan;
    private String pesan;
    private Object[][] list;
    private Object[][] listGaji;

    private final Koneksi koneksi = new Koneksi();
    private final PesanDialog pesanDialog = new PesanDialog();
    private byte[] pdfasbytes;

    // Getter dan Setter
    public String getKtp() {
        return ktp;
    }

    public void setKtp(String ktp) {
        this.ktp = ktp;
    }

    public String getKodePekerjaan() {
        return kodePekerjaan;
    }

    public void setKodePekerjaan(String kodePekerjaan) {
        this.kodePekerjaan = kodePekerjaan;
    }

    public String getGajiBersih() {
        return gajiBersih;
    }

    public void setGajiBersih(String gajiBersih) {
        this.gajiBersih = gajiBersih;
    }

    public String getGajiKotor() {
        return gajiKotor;
    }

    public void setGajiKotor(String gajiKotor) {
        this.gajiKotor = gajiKotor;
    }

    public String getTunjangan() {
        return tunjangan;
    }

    public void setTunjangan(String tunjangan) {
        this.tunjangan = tunjangan;
    }

    public String getPesan() {
        return pesan;
    }

    public Object[][] getList() {
        return list;
    }

    public void setList(Object[][] list) {
        this.list = list;
    }

    public Object[][] getListGaji() {
        return listGaji;
    }

    public void setListGaji(Object[][] listGaji) {
        this.listGaji = listGaji;
    }

    // Method baca() - untuk cari data gaji berdasarkan KTP dan Kode Pekerjaan
    public boolean baca(String ktpCari, String kodePekerjaanCari) {
        boolean adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {
            String SQLStatement = "";
            PreparedStatement preparedStatement = null;
            ResultSet rset = null;

            try {
                SQLStatement = "SELECT ktp, kodepekerjaan, gajibersih, gajikotor, tunjangan " +
                              "FROM tbgaji WHERE ktp = ? AND kodepekerjaan = ?";
                preparedStatement = connection.prepareStatement(SQLStatement);
                preparedStatement.setString(1, ktpCari);
                preparedStatement.setString(2, kodePekerjaanCari);
                rset = preparedStatement.executeQuery();

                if (rset.next()) {
                    this.ktp = rset.getString("ktp");
                    this.kodePekerjaan = rset.getString("kodepekerjaan");
                    this.gajiBersih = rset.getString("gajibersih");
                    this.gajiKotor = rset.getString("gajikotor");
                    this.tunjangan = rset.getString("tunjangan");
                } else {
                    adaKesalahan = true;
                    pesan = "Data gaji tidak ditemukan";
                }

                rset.close();
                preparedStatement.close();
                connection.close();

            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membaca data gaji\n" + ex + "\n" + SQLStatement;
            }

        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }

        return !adaKesalahan;
    }
    
    // INSERT THIS METHOD INTO Gaji.java

    // Method bacaByKtp() - Find salary data using ONLY KTP
    public boolean bacaByKtp(String ktpCari) {
        boolean adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {
            String SQLStatement = "";
            PreparedStatement preparedStatement = null;
            ResultSet rset = null;

            try {
                // Select the first entry found for this KTP
                SQLStatement = "SELECT ktp, kodepekerjaan, gajibersih, gajikotor, tunjangan " +
                              "FROM tbgaji WHERE ktp = ?";
                preparedStatement = connection.prepareStatement(SQLStatement);
                preparedStatement.setString(1, ktpCari);
                rset = preparedStatement.executeQuery();

                if (rset.next()) {
                    this.ktp = rset.getString("ktp");
                    this.kodePekerjaan = rset.getString("kodepekerjaan");
                    this.gajiBersih = rset.getString("gajibersih");
                    this.gajiKotor = rset.getString("gajikotor");
                    this.tunjangan = rset.getString("tunjangan");
                } else {
                    adaKesalahan = true;
                    pesan = "Data gaji tidak ditemukan untuk KTP ini";
                }

                rset.close();
                preparedStatement.close();
                connection.close();

            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membaca data gaji\n" + ex + "\n" + SQLStatement;
            }

        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }

        return !adaKesalahan;
    }

    // Method simpan() - untuk menyimpan data gaji
    public boolean simpan() {
        boolean adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {
            String SQLStatement = "";
            PreparedStatement preparedStatement = null;

            try {
                // Cek apakah data sudah ada
                SQLStatement = "SELECT COUNT(*) as jml FROM tbgaji WHERE ktp = ? AND kodepekerjaan = ?";
                preparedStatement = connection.prepareStatement(SQLStatement);
                preparedStatement.setString(1, ktp);
                preparedStatement.setString(2, listGaji[0][0].toString());
                ResultSet rset = preparedStatement.executeQuery();
                
                int jumlahData = 0;
                if (rset.next()) {
                    jumlahData = rset.getInt("jml");
                }
                rset.close();
                preparedStatement.close();

                // Loop untuk setiap data gaji dalam listGaji
                for (int i = 0; i < listGaji.length; i++) {
                    String kodePekerjaanLoop = listGaji[i][0].toString();
                    String gajiBersihLoop = listGaji[i][1].toString();
                    String gajiKotorLoop = listGaji[i][2].toString();
                    String tunjanganLoop = listGaji[i][3].toString();

                    if (jumlahData > 0) {
                        // Update data yang sudah ada
                        SQLStatement = "UPDATE tbgaji SET gajibersih = ?, gajikotor = ?, tunjangan = ? " +
                                      "WHERE ktp = ? AND kodepekerjaan = ?";
                        preparedStatement = connection.prepareStatement(SQLStatement);
                        preparedStatement.setString(1, gajiBersihLoop);
                        preparedStatement.setString(2, gajiKotorLoop);
                        preparedStatement.setString(3, tunjanganLoop);
                        preparedStatement.setString(4, ktp);
                        preparedStatement.setString(5, kodePekerjaanLoop);
                    } else {
                        // Insert data baru
                        SQLStatement = "INSERT INTO tbgaji (ktp, kodepekerjaan, gajibersih, gajikotor, tunjangan) " +
                                      "VALUES (?, ?, ?, ?, ?)";
                        preparedStatement = connection.prepareStatement(SQLStatement);
                        preparedStatement.setString(1, ktp);
                        preparedStatement.setString(2, kodePekerjaanLoop);
                        preparedStatement.setString(3, gajiBersihLoop);
                        preparedStatement.setString(4, gajiKotorLoop);
                        preparedStatement.setString(5, tunjanganLoop);
                    }

                    int jumlahSimpan = preparedStatement.executeUpdate();

                    if (jumlahSimpan < 1) {
                        adaKesalahan = true;
                        pesan = "Gagal menyimpan data gaji";
                    }
                    
                    preparedStatement.close();
                }

                connection.close();

            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat menyimpan data gaji\n" + ex.getMessage();
            }

        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }
        
        return !adaKesalahan;
    }
    
    // Method hapus() - untuk menghapus data gaji
    public boolean hapus(String ktpHapus, String kodePekerjaanHapus) {
        boolean adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {
            String SQLStatement = "";
            PreparedStatement preparedStatement = null;

            try {
                SQLStatement = "DELETE FROM tbgaji WHERE ktp = ? AND kodepekerjaan = ?";
                preparedStatement = connection.prepareStatement(SQLStatement);
                preparedStatement.setString(1, ktpHapus);
                preparedStatement.setString(2, kodePekerjaanHapus);
                
                int jumlahHapus = preparedStatement.executeUpdate();

                if (jumlahHapus < 1) {
                    adaKesalahan = true;
                    pesan = "Data tidak ditemukan atau gagal dihapus";
                }

                preparedStatement.close();
                connection.close();

            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat menghapus data gaji\n" + ex + "\n" + SQLStatement;
            }

        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }

        return !adaKesalahan;
    }

    // Method bacaData() - untuk membaca list data gaji
    public boolean bacaData(int mulai, int jumlah) {
        boolean adaKesalahan = false;
        Connection connection;
        list = new Object[0][0];

        if ((connection = koneksi.getConnection()) != null) {
            String SQLStatement = "";
            PreparedStatement preparedStatement = null;
            ResultSet rset = null;

            try {
                SQLStatement = "SELECT g.ktp, k.nama, g.kodepekerjaan, p.namapekerjaan, " +
                              "g.gajibersih, g.gajikotor, g.tunjangan " +
                              "FROM tbgaji g " +
                              "JOIN tbkaryawan k ON g.ktp = k.ktp " +
                              "JOIN tbpekerjaan p ON g.kodepekerjaan = p.kodepekerjaan " +
                              "ORDER BY g.ktp, g.kodepekerjaan LIMIT ?, ?";
                preparedStatement = connection.prepareStatement(
                    SQLStatement,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, 
                    ResultSet.CONCUR_READ_ONLY
                );
                preparedStatement.setInt(1, mulai);
                preparedStatement.setInt(2, jumlah);
                rset = preparedStatement.executeQuery();
                
                // Hitung jumlah baris hasil query
                rset.last();
                int baris = rset.getRow();
                rset.beforeFirst();
                
                // Inisialisasi array
                list = new Object[baris][7];
                
                // Isi array dengan data
                int i = 0;
                while (rset.next()) {
                    list[i][0] = rset.getString("ktp");
                    list[i][1] = rset.getString("nama");
                    list[i][2] = rset.getString("kodepekerjaan");
                    list[i][3] = rset.getString("namapekerjaan");
                    list[i][4] = rset.getString("gajibersih");
                    list[i][5] = rset.getString("gajikotor");
                    list[i][6] = rset.getString("tunjangan");
                    i++;
                }
                
                rset.close();
                preparedStatement.close();
                connection.close();
                
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbgaji\n" + ex + "\n" + SQLStatement;
            }
            
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }

        return !adaKesalahan;
    }

    // Method untuk generate laporan dengan JasperReports
    public boolean generateReport(String namaFile, String formatFile, String pathJrxml) {
        boolean adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {
            try {
                // Load JRXML dan compile
                JasperDesign jasperDesign = JRXmlLoader.load(pathJrxml);
                JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

                // Buat parameter (jika diperlukan)
                HashMap<String, Object> parameters = new HashMap<>();

                // Fill report dengan data dari database
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

                // Export sesuai format yang diminta
                JRExporter exporter = null;
                
                switch (formatFile.toLowerCase()) {
                    case "pdf":
                        exporter = new JRPdfExporter();
                        break;
                    case "xls":
                        exporter = new JRXlsExporter();
                        break;
                    case "xlsx":
                        exporter = new JRXlsxExporter();
                        break;
                    case "docx":
                        exporter = new JRDocxExporter();
                        break;
                    case "rtf":
                        exporter = new JRRtfExporter();
                        break;
                    case "odt":
                        exporter = new JROdtExporter();
                        break;
                    default:
                        exporter = new JRPdfExporter();
                        break;
                }

                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, namaFile);
                exporter.exportReport();

                connection.close();

            } catch (JRException | SQLException ex) {
                adaKesalahan = true;
                pesan = "Gagal membuat laporan: " + ex.getMessage();
            }

        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }

        return !adaKesalahan;
    }

    // Method untuk generate laporan dengan ResultSet
    public boolean generateReportFromResultSet(String namaFile, String formatFile, String pathJrxml, ResultSet resultSet) {
        boolean adaKesalahan = false;

        try {
            // Load JRXML dan compile
            JasperDesign jasperDesign = JRXmlLoader.load(pathJrxml);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            // Buat data source dari ResultSet
            JRResultSetDataSource dataSource = new JRResultSetDataSource(resultSet);

            // Buat parameter (jika diperlukan)
            HashMap<String, Object> parameters = new HashMap<>();

            // Fill report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Export sesuai format yang diminta
            JRExporter exporter = null;
            
            switch (formatFile.toLowerCase()) {
                case "pdf":
                    exporter = new JRPdfExporter();
                    break;
                case "xls":
                    exporter = new JRXlsExporter();
                    break;
                case "xlsx":
                    exporter = new JRXlsxExporter();
                    break;
                case "docx":
                    exporter = new JRDocxExporter();
                    break;
                case "rtf":
                    exporter = new JRRtfExporter();
                    break;
                case "odt":
                    exporter = new JROdtExporter();
                    break;
                default:
                    exporter = new JRPdfExporter();
                    break;
            }

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, namaFile);
            exporter.exportReport();

        } catch (JRException ex) {
            adaKesalahan = true;
            pesan = "Gagal membuat laporan: " + ex.getMessage();
        }

        return !adaKesalahan;
    }

    

public boolean cetakLaporan(String opsi, String ktp, int ruangDipilih, String format, String reportPath) {
    boolean adaKesalahan = false;
    Connection connection;
    
    // Perlu import java.io.ByteArrayOutputStream
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); 

    if ((connection = koneksi.getConnection()) != null) {
        String SQLStatement = "";
        PreparedStatement preparedStatement = null;
        ResultSet rset = null;
        
        // --- 1. Tentukan Query SQL berdasarkan OPSI filter (TELAH DIKOREKSI) ---
        
        // Base Query yang benar
       String BASE_SQL = "SELECT k.ktp, k.nama, p.namapekerjaan, g.gajibersih, g.tunjangan, g.gajikotor, p.kodepekerjaan " 
               + "FROM tbgaji g JOIN tbkaryawan k ON g.ktp = k.ktp " 
               + "JOIN tbpekerjaan p ON g.kodepekerjaan = p.kodepekerjaan ";

        if (opsi.equalsIgnoreCase("KTP")) {
            SQLStatement = BASE_SQL + "WHERE k.ktp = ?";
        } else if (opsi.equalsIgnoreCase("ruang")) {
            SQLStatement = BASE_SQL + "WHERE k.ruang = ?";
        } else { // Semua data
            SQLStatement = BASE_SQL;
        }
        
        try {
            preparedStatement = connection.prepareStatement(SQLStatement);
            
            // --- 2. Set Parameter Query (Jika ada filter) ---
            if (opsi.equalsIgnoreCase("KTP")) {
                preparedStatement.setString(1, ktp);
            } else if (opsi.equalsIgnoreCase("ruang")) {
                preparedStatement.setInt(1, ruangDipilih);
            }
            
            rset = preparedStatement.executeQuery();

            // --- 3. Load & Fill Report ---
            
            // JRLoader.loadObject digunakan untuk memuat file .jasper
            JasperReport jasperReport = (JasperReport) net.sf.jasperreports.engine.util.JRLoader.loadObject(new File(reportPath));
            
            JRResultSetDataSource dataSource = new JRResultSetDataSource(rset);
            HashMap<String, Object> parameters = new HashMap<>();
         
            
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // --- 4. Export ke Byte Array ---
            JRExporter exporter = null;

            switch (format.toLowerCase()) {
                case "pdf":
                    exporter = new JRPdfExporter();
                    break;
                case "xls":
                    exporter = new JRXlsExporter();
                    break;
                case "xlsx":
                    exporter = new JRXlsxExporter();
                    break;
                case "docx":
                    exporter = new JRDocxExporter();
                    break;
                case "rtf":
                    exporter = new JRRtfExporter();
                    break;
                case "odt":
                    exporter = new JROdtExporter();
                    break;
                default:
                    exporter = new JRPdfExporter(); // Default ke PDF
                    break;
            }

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
            exporter.exportReport();
            
            // Simpan hasil byte array
            pdfasbytes = outputStream.toByteArray();
            
            // 5. Close resources
            outputStream.close();
            rset.close();
            preparedStatement.close();
            connection.close();
            
        } catch (JRException | SQLException | java.io.IOException ex) {
            adaKesalahan = true;
            pesan = "Gagal membuat laporan: " + ex.getMessage();
        }
    } else {
        adaKesalahan = true;
        pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
    }
    
    return !adaKesalahan;
}

    public byte[] getPdfasbytes() {
        return pdfasbytes;
    }
    
}