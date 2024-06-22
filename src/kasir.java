
import com.mysql.cj.xdevapi.Statement;
import com.sun.jdi.connect.spi.Connection;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Lenovo
 */
public class kasir extends javax.swing.JFrame {

    /**
     * Creates new form kasir
     */
    private Statement St;
    private Connection Con;
    private ResultSet Rs;
    private String sql ="";
    
    NumberFormat nf = NumberFormat.getIntegerInstance(new Locale("in", "kode "));
    public kasir() {
        initComponents();
        KodeBarang();
        Total();
        Diskon();
        autonumber();
    }
    
    private void autonumber(){
        try {
            java.sql.Connection c = Koneksi.getKoneksiData();
            java.sql.Statement s = c.createStatement();
            String sql = "SELECT * FROM penjualanrinci ORDER BY NoFaktur DESC";
            ResultSet r = s.executeQuery(sql);
            if (r.next()) {
                String NoFaktur = r.getString("NoFaktur").substring(2);
                String TR = "" +(Integer.parseInt(NoFaktur)+1);
                String Nol = "";
                
                if(TR.length()==1)
                {Nol = "000";}
                else if(TR.length()==2)
                {Nol = "00";}
                else if(TR.length()==3)
                {Nol = "0";}
                else if(TR.length()==4)
                {Nol = "";}
                txtNoFaktur.setText("TR" + Nol + TR);
            } else {
                txtNoFaktur.setText("TR0001");
            }
            r.close();
            s.close();
        } catch (Exception e) {
            System.out.println("autonumber error");
        }
    }
    
    
    private void Diskon(){
        txtDiskon.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
            int diskon, hasil_diskon;
        
            diskon = Integer.parseInt(txtJumlahHarga.getText().replace(".", "")) * Integer.parseInt(txtDiskon.getText().replace("", "")) / 100;
            hasil_diskon = Integer.parseInt(txtJumlahHarga.getText().replace(".", ""))
            - diskon;
       
            txtHasilDIskon.setText(nf.format(diskon));
            // ppn*
            int hasil_ppn = 0;
            if (chkPPN.isSelected()){
                hasil_ppn = hasil_diskon * 10 / 100;
                txtHasilPPN.setText(nf.format(hasil_ppn));
            }else{
                hasil_ppn = hasil_diskon * 0 / 100;
                txtHasilPPN.setText(nf.format(hasil_ppn));
            }

            // total bersih semua

            int totalBersih;
            totalBersih = hasil_ppn + hasil_diskon;
            lblJumlahHarga.setText("Rp. " + nf.format(totalBersih));
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateDiskon();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateDiskon();
        }
    });

    // Memperbaiki perhitungan diskon
    updateDiskon();
}

    private void updateDiskon() {
        try {
            int harga = Integer.parseInt(txtJumlahHarga.getText().replace(".", ""));
            int diskon = Integer.parseInt(txtDiskon.getText().trim()); // Mengubah teks diskon menjadi integer

            int hasil_diskon = harga * diskon / 100;
            txtHasilDIskon.setText(nf.format(hasil_diskon));

            // Menghitung PPN
            int hasil_ppn = 0;
            if (chkPPN.isSelected()) {
                hasil_ppn = hasil_diskon * 10 / 100;
                txtHasilPPN.setText(nf.format(hasil_ppn));
            } else {
                txtHasilPPN.setText(nf.format(hasil_ppn));
            }

            // Menghitung total bersih
            int totalBersih = hasil_diskon + hasil_ppn;
            lblJumlahHarga.setText("Rp. " + nf.format(totalBersih));
        } catch (NumberFormatException ex) {
            // Handle jika input tidak valid
            txtHasilDIskon.setText("0");
            txtHasilPPN.setText("0");
            lblJumlahHarga.setText("Rp. 0");
        }
    }

    private void Total(){
        txtQuantity.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
    try {
        int harga = Integer.parseInt(txtHarga.getText().replace(".", ""));
        int quantity = 0;
        
        if (!txtQuantity.getText().isEmpty()) {
            quantity = Integer.parseInt(txtQuantity.getText());
        }
        
        int totalHarga = harga * quantity;
        txtTotalHarga.setText(nf.format(totalHarga));
    } catch (NumberFormatException ex) {
        // Handle the case where parsing fails
        ex.printStackTrace(); // At least print the stack trace for debugging
        // Optionally, you can show a message to the user indicating the input format is incorrect
    }
}

            @Override
            public void removeUpdate(DocumentEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                try {
        int harga = Integer.parseInt(txtHarga.getText().replace(".", ""));
        int quantity = 0;
        
        if (!txtQuantity.getText().isEmpty()) {
            quantity = Integer.parseInt(txtQuantity.getText());
        }
        
        int totalHarga = harga * quantity;
        txtTotalHarga.setText(nf.format(totalHarga));
    } catch (NumberFormatException ex) {
        // Handle the case where parsing fails
        ex.printStackTrace(); // At least print the stack trace for debugging
        // Optionally, you can show a message to the user indicating the input format is incorrect
    }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }
    private void KodeBarang(){
        txtKodeBarang.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {  
           //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
           
           String kodeBarang;
           kodeBarang = (String) txtKodeBarang.getText();
           
           switch(kodeBarang){
               case "B001" :
                   txtNamaBarang.setText("Adidas Samba");
                   txtHarga.setText(nf.format(2300000));
                   txtQuantity.grabFocus();
                   break;
               case "B002" :
                   txtNamaBarang.setText("Nike Air Jordan");
                   txtHarga.setText(nf.format(3500000));
                   txtQuantity.grabFocus();
                   break;
               case "B003" :
                   txtNamaBarang.setText("Kodachi");
                   txtHarga.setText(nf.format(1200000));
                   txtQuantity.grabFocus();
                   break;
               case "B004" :
                   txtNamaBarang.setText("Converse");
                   txtHarga.setText(nf.format(7500000));
                   txtQuantity.grabFocus();
                   break;
               case "B005" :
                   txtNamaBarang.setText("Docmart");
                   txtHarga.setText(nf.format(3200000));
                   txtQuantity.grabFocus();
                   break;
               default:
                   txtNamaBarang.setText("");
                   txtHarga.setText("");
           }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                //Throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                
                 String kodeBarang;
           kodeBarang = (String) txtKodeBarang.getText();
           
           switch(kodeBarang){
               case "B001" :
                   txtNamaBarang.setText("Adidas Samba");
                   txtHarga.setText(nf.format(2300000));
                   txtQuantity.grabFocus();
                   break;
               case "B002" :
                   txtNamaBarang.setText("Nike Air Jordan");
                   txtHarga.setText(nf.format(3500000));
                   txtQuantity.grabFocus();
                   break;
               case "B003" :
                   txtNamaBarang.setText("Kodachi");
                   txtHarga.setText(nf.format(1200000));
                   txtQuantity.grabFocus();
                   break;
               case "B004" :
                   txtNamaBarang.setText("Converse");
                   txtHarga.setText(nf.format(7500000));
                   txtQuantity.grabFocus();
                   break;
               case "B005" :
                   txtNamaBarang.setText("Docmart");
                   txtHarga.setText(nf.format(3200000));
                   txtQuantity.grabFocus();
                   break;
               default:
                   txtNamaBarang.setText("");
                   txtHarga.setText("");
           }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblJumlahHarga = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNoFaktur = new javax.swing.JTextField();
        txtKodeBarang = new javax.swing.JTextField();
        txtNamaBarang = new javax.swing.JTextField();
        txtHarga = new javax.swing.JTextField();
        txtQuantity = new javax.swing.JTextField();
        txtTotalHarga = new javax.swing.JTextField();
        txcari = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblList = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txtJumlahHarga = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtDiskon = new javax.swing.JTextField();
        txtHasilDIskon = new javax.swing.JTextField();
        chkPPN = new javax.swing.JCheckBox();
        txtHasilPPN = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        lblJmlhQuantity = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblJumlahHarga.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        lblJumlahHarga.setText("Rp.");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("No. Faktur ");

        jLabel3.setText("Kode Barang");

        txtNoFaktur.setEnabled(false);
        txtNoFaktur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoFakturActionPerformed(evt);
            }
        });

        txtHarga.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        txtQuantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuantityActionPerformed(evt);
            }
        });

        txtTotalHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalHargaActionPerformed(evt);
            }
        });

        txcari.setText("Cari");
        txcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txcariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtNoFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtKodeBarang)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalHarga, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txcari)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNoFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txcari))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtKodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Kode Barang", "Nama Barang", "Harga", "Quantity", "Total Harga"
            }
        ));
        jScrollPane1.setViewportView(tblList);

        jLabel4.setText("Jumlah Harga");

        txtJumlahHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumlahHargaActionPerformed(evt);
            }
        });

        jLabel5.setText("Diskon %");

        chkPPN.setText("PPN 10 &");
        chkPPN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPPNActionPerformed(evt);
            }
        });

        jLabel6.setText("Total Harga");

        txtTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalActionPerformed(evt);
            }
        });

        jLabel7.setText("Item Yang Dibeli : ");

        lblJmlhQuantity.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblJmlhQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(chkPPN)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtHasilPPN))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtJumlahHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtDiskon, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtHasilDIskon))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTotal))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblJumlahHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblJumlahHarga)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtJumlahHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(lblJmlhQuantity))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtDiskon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHasilDIskon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(chkPPN)
                    .addComponent(txtHasilPPN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(864, 560));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuantityActionPerformed
        // TODO add your handling code here:
        if(txtQuantity.getText().equals("")){
            return;
        }else{
            DefaultTableModel model = (DefaultTableModel) tblList.getModel();
            
            Object obj [] = new Object[6];
            obj [1] = txtKodeBarang.getText();
            obj [2] = txtNamaBarang.getText();
            obj [3] = txtHarga.getText();
            obj [4] = txtQuantity.getText();
            obj [5] = txtTotalHarga.getText();
            
            model.addRow(obj);
            
            int baris = model.getRowCount();
            for(int a = 0; a < baris; a++){
                String no = String.valueOf(a + 1);
                model.setValueAt(no + ".", a, 0);
            }
            tblList.setRowHeight(30);
            lblJmlhQuantity.setText((String.valueOf(baris)));
            
            jmlTotalHarga();
            bersih();
        }
    }//GEN-LAST:event_txtQuantityActionPerformed

    private void txtJumlahHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJumlahHargaActionPerformed

    private void chkPPNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPPNActionPerformed
        // TODO add your handling code here:
        jmlTotalHarga();
    }//GEN-LAST:event_chkPPNActionPerformed

    private void txcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txcariActionPerformed
        // TODO add your handling code here:
        ListBarang a = new ListBarang();
        a.setVisible(true);
    }//GEN-LAST:event_txcariActionPerformed

    private void txtNoFakturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoFakturActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoFakturActionPerformed

    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalActionPerformed

    private void txtTotalHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalHargaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new kasir().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkPPN;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblJmlhQuantity;
    private javax.swing.JLabel lblJumlahHarga;
    private javax.swing.JTable tblList;
    private javax.swing.JButton txcari;
    private javax.swing.JTextField txtDiskon;
    public static javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtHasilDIskon;
    private javax.swing.JTextField txtHasilPPN;
    private javax.swing.JTextField txtJumlahHarga;
    public static javax.swing.JTextField txtKodeBarang;
    public static javax.swing.JTextField txtNamaBarang;
    private javax.swing.JTextField txtNoFaktur;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTotalHarga;
    // End of variables declaration//GEN-END:variables

    private void jmlTotalHarga() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        int sub_total = 0;
        for(int a = 0; a <  tblList.getRowCount(); a ++){
            sub_total += Integer.parseInt((String) 
                    tblList.getValueAt(a, 5).toString().replace(".", ""));
        }
        
        txtJumlahHarga.setText(nf.format(sub_total));
        
        // diskon
       int diskon, hasil_diskon;
       diskon = Integer.parseInt(txtJumlahHarga.getText().replace(".", "")) * Integer.parseInt(txtDiskon.getText().replace("", "0")) / 100;
        
       
        
       hasil_diskon = Integer.parseInt(txtJumlahHarga.getText().replace(".", ""))
            - diskon;
       
       // ppn*
       int hasil_ppn = 0;
       if (chkPPN.isSelected()){
           hasil_ppn = hasil_diskon * 10 / 100;
           txtHasilPPN.setText(nf.format(hasil_ppn));
       }else{
           hasil_ppn = hasil_diskon * 0 / 100;
           txtHasilPPN.setText(nf.format(hasil_ppn));
       }
       
       // total bersih semua
       
       int totalBersih;
       totalBersih = hasil_ppn + hasil_diskon;
       lblJumlahHarga.setText("Rp. " + nf.format(totalBersih));
    }

    private void bersih() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
       txtKodeBarang.setText("");
       txtKodeBarang.grabFocus();
       txtQuantity.setText("");
       txtTotalHarga.setText("");
    }  
            
}
