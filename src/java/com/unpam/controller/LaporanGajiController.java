/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.unpam.controller;

import com.unpam.model.Gaji;
import com.unpam.view.MainForm;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.OutputStream;

/**
 *
 * @author Verrel
 */
@WebServlet(name = "LaporanGajiController", urlPatterns = {"/LaporanGajiController"})
public class LaporanGajiController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String[][] formatTypeData = {
        {"PDF (Portable Document Format)", "pdf", "application/pdf"},
        {"XLSX (Microsoft Excel)", "xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
        {"XLS (Microsoft Excel 97-2003)", "xls", "application/vnd.ms-excel"},
        {"DOCX (Microsoft Word)", "docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
        {"ODT (OpenDocument Text)", "odt", "application/vnd.oasis.opendocument.text"},
        {"RTF (Rich Text Format)", "rtf", "text/rtf"}
    };

    // 2. Mengambil Parameter dan Session [cite: 99]
    HttpSession session = request.getSession(true);
    String userName = "";
    
    String tombol = request.getParameter("tombol");
    String opsi = request.getParameter("opsi");
    String ktp = request.getParameter("ktp"); // Di dokumen tertulis 'kup', saya koreksi jadi 'ktp'
    String ruang = request.getParameter("ruang");
    String formatType = request.getParameter("formatType");

    // Inisialisasi nilai default jika null
    if (tombol == null) tombol = "";
    if (ktp == null) ktp = "";
    if (opsi == null) opsi = "";
    if (ruang == null) ruang = "0";

    String keterangan = "<br>";
    int noType = 0;

    // Menentukan index format laporan yang dipilih
    for (int i = 0; i < formatTypeData.length; i++) {
        if (formatTypeData[i][0].equals(formatType)) {
            noType = i;
        }
    }

    try {
        userName = session.getAttribute("userName").toString(); // Di dokumen tertulis 'userlane'
    } catch (Exception ex) {}

    // Cek apakah user sudah login
    if (!((userName == null) || userName.equals(""))) {
        boolean opsiSelected = false;
        
       

        // --- LOGIKA CETAK LAPORAN ---
        if (tombol.equals("Cetak")) { 
            Gaji gaji = new Gaji();
            int ruangDipilih = 0;
            try {
                ruangDipilih = Integer.parseInt(ruang);
            } catch (NumberFormatException ex) {}
            
            String realReportPath = getServletConfig().getServletContext().getRealPath("reports/GajiReport.jasper");
            

            // Memproses laporan menggunakan JasperReports
            // Path file laporan mengarah ke folder "reports/GajiReport.jasper"
            if (gaji.cetakLaporan(opsi, ktp, ruangDipilih, formatTypeData[noType][1], realReportPath)){
                byte[] pdfasbytes = gaji.getPdfasbytes();

                try (OutputStream outStream = response.getOutputStream()) {
                    response.setHeader("Content-Disposition", "inline; filename=GajiReport." + formatTypeData[noType][1]);
                    response.setContentType(formatTypeData[noType][2]);
                    response.setContentLength(pdfasbytes.length);
                    outStream.write(pdfasbytes, 0, pdfasbytes.length);
                    outStream.flush();
                } // Stream otomatis close di sini
            } else {
                keterangan += gaji.getPesan();
            }
        } 
        
        // --- LOGIKA TAMPILAN FORM (HTML) ---
        // Bagian ini menyusun HTML untuk form pilihan laporan [cite: 109 - 263]
        
        String konten = "<h2>Mencetak Gaji</h2>";
        konten += "<form action='LaporanGajiController' method='post'>";
        konten += "<table>";
        
        // Pilihan Berdasarkan KTP
        konten += "<tr>";
        if (opsi.equalsIgnoreCase("KTP")) {
            konten += "<td align='right'><input type='radio' checked name='opsi' value='KTP'></td>";
            opsiSelected = true;
        } else {
            konten += "<td align='right'><input type='radio' name='opsi' value='KTP'></td>";
        }
        konten += "<td align='left'>KTP</td>";
        konten += "<td align='left'><input type='text' value='" + ktp + "' name='ktp' maxlength='15' size='15'></td>";
        konten += "</tr>";

        // Pilihan Berdasarkan Ruang
        konten += "<tr>";
        if (opsi.equals("ruang")) {
            konten += "<td align='right'><input type='radio' checked name='opsi' value='ruang'></td>";
            opsiSelected = true;
        } else {
            konten += "<td align='right'><input type='radio' name='opsi' value='ruang'></td>";
        }
        konten += "<td align='left'>Ruang</td>";
        konten += "<td align='left'>";
        konten += "<select name='ruang'>";
        konten += "<option selected value=0>Semua</option>";
        
        // Loop opsi ruang 1-14
        for (int i = 1; i <= 14; i++) {
            if (i == Integer.parseInt(ruang)) {
                konten += "<option selected value=" + i + ">" + i + "</option>";
            } else {
                konten += "<option value=" + i + ">" + i + "</option>";
            }
        }
        konten += "</select></td></tr>";

        // Pilihan Semua Data
        konten += "<tr>";
        if (!opsiSelected) {
            konten += "<td align='right'><input type='radio' checked name='opsi' value='Semua'></td>";
        } else {
            konten += "<td align='right'><input type='radio' name='opsi' value='Semua'></td>";
        }
        konten += "<td align='left'>Semua</td><td><br></td></tr>";

        // Spacer
        konten += "<tr><td colspan='3'><br></td></tr>";

        // Pilihan Format Laporan (PDF/Excel/dll)
        konten += "<tr>";
        konten += "<td>Format Laporan</td>";
        konten += "<td colspan=2><select name='formatType'>";
        for (String[] formatLaporan : formatTypeData) {
            if (formatLaporan[0].equals(formatType)) {
                konten += "<option selected value='" + formatLaporan[0] + "'>" + formatLaporan[0] + "</option>";
            } else {
                konten += "<option value='" + formatLaporan[0] + "'>" + formatLaporan[0] + "</option>";
            }
        }
        konten += "</select></td></tr>";

        // Menampilkan Pesan Error/Info
        konten += "<tr><td colspan='3'><b>" + keterangan.replaceAll("\n", "<br>").replaceAll(";", ",") + "</b></td></tr>";

        // Tombol Submit
        konten += "<tr><td colspan='3' align='center'><input type='submit' name='tombol' value='Cetak' style='width: 100px'></td></tr>";
        
        konten += "</table></form>";

        // Tampilkan ke Main Form
        new MainForm().tampilkan(konten, request, response); // [cite: 269]

    } else {
        // Jika belum login, redirect ke halaman awal
        response.sendRedirect("."); // [cite: 270]
    }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}