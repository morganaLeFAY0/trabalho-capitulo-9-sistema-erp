/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Telas;

import Conexao.ModuloConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author beto_
 */
public class TelaProduto extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
        java.text.SimpleDateFormat formatador = 
            new java.text.SimpleDateFormat("dd/MM/yyyy");
        
    int controle;
    /**
     * Creates new form TelaProduto
     */
    public TelaProduto() {
        initComponents();
        
                
        conexao = ModuloConexao.conector(); 
        
  //      jdcDataNascimento.setDateFormatString("dd/MM/yyyy");
        
    }
    private void pesquisarProduto(){
      String sql = " select idProduto as Codigo, "
              + "Descricao_Produto as Nome, "
              + "ModeloImp_Produto as Modelo,  "
              + "MarcaImp_Produto as Marca, "
              + "Cor_Produto as Cor, "
              + "PrecoVenda_Produto as Preço, "
              + "Estoque_Produto as Estoque, "
              + "QtdeMinima_Produto as Qtde, "
              + "Status_Produto as Estado "
              + "from produto "
              + "where Descricao_Produto like ?";
        try {
            pst=conexao.prepareStatement(sql);
            pst.setString(1, txtPesquisar.getText() + "%");
            rs=pst.executeQuery();
            tblPesquisar.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } // fim try        
    }  //fim metodo pesquisar vendedor
    
    private void carregarCampos(){
        int carga = tblPesquisar.getSelectedRow();
        txtCodigo.setText(tblPesquisar.getModel().
                getValueAt(carga,0).toString());      
        txtNome.setText(tblPesquisar.getModel().
                getValueAt(carga,1).toString()); 
        txtModelo.setText(tblPesquisar.getModel().
                getValueAt(carga,2).toString()); 
        txtMarca.setText(tblPesquisar.getModel().
                getValueAt(carga,3).toString()); 
        txtCor.setText(tblPesquisar.getModel().
                getValueAt(carga,4).toString());            
         ftxtPreco.setText(tblPesquisar.getModel().
                getValueAt(carga,5).toString());
         ftxtEstoque.setText(tblPesquisar.getModel().
                getValueAt(carga,6).toString());     
         ftxtQtdemin.setText(tblPesquisar.getModel().
                getValueAt(carga,7).toString());    
          
         String status = tblPesquisar.getModel().
                getValueAt(carga, 8).toString();
                
        if (status.equals("A"))
           {
            rbtAtivo.setSelected(true);
           } 
          else
           {
           rbtInativo.setSelected(true);
           }     
         
         btnIncluir.setEnabled(false);
         Desabilitar();
    }  // fim metodo carregarCampos
    
    private void Desabilitar(){
         txtCodigo.setEditable(false);
         txtNome.setEditable(false);
         txtModelo.setEditable(false);
         txtMarca.setEditable(false);
         txtCor.setEditable(false);
         ftxtPreco.setEditable(false);
         ftxtEstoque.setEditable(false);       
         ftxtQtdemin.setEditable(false);

    } // fim metodo desabilitar
      private void Habilitar(){
         txtCodigo.setEditable(true);
         txtNome.setEditable(true);
         txtModelo.setEditable(true);
         txtMarca.setEditable(true);
         txtCor.setEditable(true);
         ftxtPreco.setEditable(true);
         ftxtEstoque.setEditable(true);       
         ftxtQtdemin.setEditable(true);
    } // fim metodo Habilitar
      
    private void Alterar(){
        String sql = "update produto set Descricao_Produto=?, "
                + "ModeloImp_Produto=?,  "
                + "MarcaImp_Produto=?, "
                + "Cor_Produto=?, "
                + "PrecoVenda_Produto=?, "
                + "Estoque_Produto=?, "
                + "QtdeMinima_Produto=?, "
                + "Status_Produto=? "
                + "where idProduto=?";   
        try {
           pst=conexao.prepareStatement(sql);
           pst.setString(1, txtNome.getText());
           pst.setString(2, txtModelo.getText());
           pst.setString(3, txtMarca.getText());
           pst.setString(4, txtCor.getText());           
           pst.setString(5, ftxtPreco.getText());
           pst.setString(6, ftxtEstoque.getText());
           pst.setString(7, ftxtQtdemin.getText());
           
           String status = rbtAtivo.isSelected() ? "A" : "I";
           pst.setString(8, status);           
       
           pst.setString(9, txtCodigo.getText());  
         
           if ((txtNome.getText().isEmpty()) || 
               (txtModelo.getText().isEmpty()) ||
               (txtMarca.getText().isEmpty()) ||   //arrumar
               (status.isEmpty())){
               JOptionPane.showMessageDialog
                   (null, "Obrigatorio todos os campo");
           } // fim if true
           else {
               int ok = pst.executeUpdate(); // atualiza tabela bd
               if (ok > 0){
                   JOptionPane.showMessageDialog(null, "Alterado Com Sucesso");
     //              limparCampos();
               }
           } // fim if false
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
        }  // fim try
    } // fim metodo salvar
    private void Incluir(){
        String sql = "insert into produto "
                + "(Descricao_Produto, "
                + "ModeloImp_Produto,  "
                + "MarcaImp_Produto, "
                + "Cor_Produto, "
                + "PrecoVenda_Produto, "
                + "Estoque_Produto, "
                + "QtdeMinima_Produto, "
                + "Status_Produto "
                + "values (?, ?, ?, ?, ?, ?, ?, ?)";   
        try {
           pst=conexao.prepareStatement(sql);
           pst.setString(1, txtNome.getText());
           pst.setString(2, txtModelo.getText());
           pst.setString(3, txtMarca.getText());
           pst.setString(4, txtCor.getText());           
           pst.setString(5, ftxtPreco.getText());
           pst.setString(6, ftxtEstoque.getText());
           pst.setString(7, ftxtQtdemin.getText());
           
           String status = rbtAtivo.isSelected() ? "A" : "I";
           pst.setString(8, status);           
       
           pst.setString(9, txtCodigo.getText());  
         
           if ((txtNome.getText().isEmpty()) || 
               (txtModelo.getText().isEmpty()) ||
               (txtMarca.getText().isEmpty()) ||   //arrumar
               (status.isEmpty())){
               JOptionPane.showMessageDialog
                   (null, "Obrigatorio todos os campo");
           } // fim if true
           else {
               int ok = pst.executeUpdate(); // atualiza tabela bd
               if (ok > 0){
                   JOptionPane.showMessageDialog(null, "Incluido Com Sucesso");
     //              limparCampos();
               }
           } // fim if false
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
        }  // fim try
    } // fim metodo salvar
      
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btgStatus = new javax.swing.ButtonGroup();
        lblProcurar = new javax.swing.JLabel();
        txtPesquisar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPesquisar = new javax.swing.JTable();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblNome = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        lblModelo = new javax.swing.JLabel();
        txtModelo = new javax.swing.JTextField();
        txtMarca = new javax.swing.JTextField();
        lblMarca = new javax.swing.JLabel();
        lblCor = new javax.swing.JLabel();
        txtCor = new javax.swing.JTextField();
        lblPreco = new javax.swing.JLabel();
        ftxtPreco = new javax.swing.JFormattedTextField();
        lblEstoque = new javax.swing.JLabel();
        ftxtEstoque = new javax.swing.JFormattedTextField();
        lblQtdemin = new javax.swing.JLabel();
        ftxtQtdemin = new javax.swing.JFormattedTextField();
        lblStatus = new javax.swing.JLabel();
        rbtAtivo = new javax.swing.JRadioButton();
        rbtInativo = new javax.swing.JRadioButton();
        btnIncluir = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Produtos");

        lblProcurar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/procurar64.png"))); // NOI18N

        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
        });

        tblPesquisar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblPesquisar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPesquisarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPesquisar);

        lblCodigo.setText("Codigo");

        lblNome.setText("Nome");

        lblModelo.setText("Modelo");

        lblMarca.setText("Marca");

        lblCor.setText("Cor");

        lblPreco.setText("Preço");

        lblEstoque.setText("Estoque");

        lblQtdemin.setText("Quantde");

        lblStatus.setText("Status");

        btgStatus.add(rbtAtivo);
        rbtAtivo.setText("Ativo");

        btgStatus.add(rbtInativo);
        rbtInativo.setText("Inativo");

        btnIncluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/adicionar64.png"))); // NOI18N

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/editar64.png"))); // NOI18N
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/lixo64.png"))); // NOI18N

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/salve64.png"))); // NOI18N
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(49, 118, Short.MAX_VALUE)
                .addComponent(lblProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(261, 261, 261))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(274, 274, 274)
                        .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 833, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(lblMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblCor, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCor, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblNome, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblPreco, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21)
                                .addComponent(ftxtPreco, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(lblEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ftxtEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblQtdemin, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ftxtQtdemin, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbtAtivo, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbtInativo, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addComponent(btnIncluir)
                        .addGap(71, 71, 71)
                        .addComponent(btnEditar)
                        .addGap(77, 77, 77)
                        .addComponent(btnExcluir)
                        .addGap(72, 72, 72)
                        .addComponent(btnSalvar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNome)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblModelo)
                    .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMarca)
                    .addComponent(lblCor)
                    .addComponent(txtCor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPreco)
                    .addComponent(ftxtPreco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEstoque)
                    .addComponent(ftxtEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblQtdemin)
                    .addComponent(ftxtQtdemin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStatus)
                    .addComponent(rbtAtivo)
                    .addComponent(rbtInativo))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(67, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(941, 507));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        // TODO add your handling code here:
        pesquisarProduto();
    }//GEN-LAST:event_txtPesquisarKeyReleased

    private void tblPesquisarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPesquisarMouseClicked
        // TODO add your handling code here:
        carregarCampos();
    }//GEN-LAST:event_tblPesquisarMouseClicked

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
       Habilitar();
        controle = 2;
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        // TODO add your handling code here:
        if (controle == 1)
            Incluir();
          else
            if (controle == 2)
                Alterar();            
         
    }//GEN-LAST:event_btnSalvarActionPerformed

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
            java.util.logging.Logger.getLogger(TelaProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaProduto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btgStatus;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnIncluir;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JFormattedTextField ftxtEstoque;
    private javax.swing.JFormattedTextField ftxtPreco;
    private javax.swing.JFormattedTextField ftxtQtdemin;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblCor;
    private javax.swing.JLabel lblEstoque;
    private javax.swing.JLabel lblMarca;
    private javax.swing.JLabel lblModelo;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblPreco;
    private javax.swing.JLabel lblProcurar;
    private javax.swing.JLabel lblQtdemin;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JRadioButton rbtAtivo;
    private javax.swing.JRadioButton rbtInativo;
    private javax.swing.JTable tblPesquisar;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCor;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtPesquisar;
    // End of variables declaration//GEN-END:variables
}
