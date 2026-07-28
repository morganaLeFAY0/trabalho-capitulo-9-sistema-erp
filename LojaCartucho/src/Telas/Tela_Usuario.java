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
public class Tela_Usuario extends javax.swing.JFrame {
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    /**
     * Creates new form Tela_Usuario
     */
    
    int controle;
    
    public Tela_Usuario() {
        initComponents();
        
        conexao = ModuloConexao.conector();
        
    }
    
    private void pesquisarUsuario(){
      String sql = "Select idUsuario as Codigo, "
              + "Nome_Usuario as nome, "
              + "Login_Usuario as Loguim, "
              + "Perfil_Usuario as Perfil, "
              + "Status_Usuario as Estado, "
              + "DataNasc_Usuario as Nascimento, "
              + "Telefone_Usuario as Fone, "
              + "Senha_Usuario as Senha "
              + "from usuario "
              + "where Nome_Usuario like ?";
      
        try {
            pst=conexao.prepareStatement(sql);
            pst.setString(1, txtPesquisar.getText() + "%");
            rs=pst.executeQuery();
            tblPesquisar.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } // fim try        
    }  //fim metodo pesquisar usuario
    
    private void carregarCampos(){
        int carga = tblPesquisar.getSelectedRow();
        txtCodigo.setText(tblPesquisar.getModel().
                getValueAt(carga,0).toString());
         txtNome.setText(tblPesquisar.getModel().
                getValueAt(carga,1).toString()); 
         txtLogin.setText(tblPesquisar.getModel().
                getValueAt(carga,2).toString());          
         cbxPerfil.setSelectedItem(tblPesquisar.getModel().
                getValueAt(carga,3).toString());        
         txtStatus.setText(tblPesquisar.getModel().
                getValueAt(carga,4).toString());
         txtDatanascimento.setText(tblPesquisar.getModel().
                getValueAt(carga,5).toString());     
         txtTelefone.setText(tblPesquisar.getModel().
                getValueAt(carga,6).toString()); 
         txtSenha.setText(tblPesquisar.getModel().
                getValueAt(carga,7).toString());

         btnIncluir.setEnabled(false);
         Desabilitar();

    }  // fim metodo carregarCampos

    private void Desabilitar(){
         txtCodigo.setEditable(false);
         txtNome.setEditable(false);
         txtLogin.setEditable(false);
         cbxPerfil.setEditable(false);
         txtStatus.setEditable(false);
         txtDatanascimento.setEditable(false);
         txtTelefone.setEditable(false);       
         txtSenha.setEditable(false);  
    }
    
    private void habilitar(){
         txtNome.setEditable(true);
         txtLogin.setEditable(true);
         cbxPerfil.setEditable(true);
         txtStatus.setEditable(true);
         txtDatanascimento.setEditable(true);
         txtTelefone.setEditable(true);       
         txtSenha.setEditable(true);      
    } // fim metodo habilitar
    
    private void Alterar(){
        String sql = "update usuario set "
                + "Nome_Usuario=?, "
                + "Login_Usuario=?, "
                + "Senha_Usuario=?, "
                + "DataNasc_Usuario=?, "
                + "Perfil_Usuario=?, "
                + "Telefone_Usuario=?, "
                + "Status_Usuario=? "
                + "where idUsuario=?";
        
        try {
           pst=conexao.prepareStatement(sql);
           pst.setString(1, txtNome.getText());
           pst.setString(2, txtLogin.getText());
           pst.setString(3, txtSenha.getText());
           pst.setString(4, txtDatanascimento.getText());
           pst.setString(5, cbxPerfil.getSelectedItem().toString());
           pst.setString(6, txtTelefone.getText());
           pst.setString(7, txtStatus.getText());
           pst.setString(8, txtCodigo.getText());
           
           if ((txtNome.getText().isEmpty()) || 
               (txtLogin.getText().isEmpty()) ||
               (txtSenha.getText().isEmpty()) ||
               (txtStatus.getText().isEmpty())){
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
        String sql = "insert into usuario "
                + "(Nome_Usuario, "
                + "Login_Usuario, "
                + "Senha_Usuario, "
                + "DataNasc_Usuario, "
                + "Perfil_Usuario, "
                + "Telefone_Usuario, "
                + "Status_Usuario) "
                + "values (?, ?, ?, ?, ?, ?, ?)";
        try {
           pst=conexao.prepareStatement(sql);
           pst.setString(1, txtNome.getText());
           pst.setString(2, txtLogin.getText());
           pst.setString(3, txtSenha.getText());
           pst.setString(4, txtDatanascimento.getText());
           pst.setString(5, cbxPerfil.getSelectedItem().toString());
           pst.setString(6, txtTelefone.getText());
           pst.setString(7, txtStatus.getText());
           
       //    pst.setString(8, txtCodigo.getText());
           
           if ((txtNome.getText().isEmpty()) || 
               (txtLogin.getText().isEmpty()) ||
               (txtSenha.getText().isEmpty()) ||
               (txtStatus.getText().isEmpty())){
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
    } // fim metodo Incluir

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btgStatus = new javax.swing.ButtonGroup();
        lblPesquisar = new javax.swing.JLabel();
        txtPesquisar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPesquisar = new javax.swing.JTable();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblNome = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        lblLogim = new javax.swing.JLabel();
        txtLogin = new javax.swing.JTextField();
        lblSenha = new javax.swing.JLabel();
        txtSenha = new javax.swing.JTextField();
        lblPerfil = new javax.swing.JLabel();
        cbxPerfil = new javax.swing.JComboBox<>();
        lblDatanascimento = new javax.swing.JLabel();
        txtDatanascimento = new javax.swing.JTextField();
        lblTelefone = new javax.swing.JLabel();
        txtTelefone = new javax.swing.JTextField();
        lblStatus = new javax.swing.JLabel();
        rdbAtivo = new javax.swing.JRadioButton();
        rdbInativo = new javax.swing.JRadioButton();
        btnIncluir = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        txtStatus = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Usuario");
        setPreferredSize(new java.awt.Dimension(600, 400));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        lblPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/procurar64.png"))); // NOI18N
        lblPesquisar.setToolTipText("Pesquisar");

        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
        });

        tblPesquisar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Nome", "Login", "Perfil", "Status"
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

        lblLogim.setText("Login");

        lblSenha.setText("Senha");

        lblPerfil.setText("Perfil");

        cbxPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Adm", "Usu", "Vis" }));
        cbxPerfil.setToolTipText("");

        lblDatanascimento.setText("Data Nascimento");

        lblTelefone.setText("Telefone");

        lblStatus.setText("Status");

        btgStatus.add(rdbAtivo);
        rdbAtivo.setText("Ativo");

        btgStatus.add(rdbInativo);
        rdbInativo.setText("Inativo");

        btnIncluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/adicionar64.png"))); // NOI18N
        btnIncluir.setToolTipText("Incluir");
        btnIncluir.setPreferredSize(new java.awt.Dimension(65, 65));
        btnIncluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncluirActionPerformed(evt);
            }
        });

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/editar64.png"))); // NOI18N
        btnEditar.setToolTipText("Editar");
        btnEditar.setPreferredSize(new java.awt.Dimension(65, 65));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/lixo64.png"))); // NOI18N
        btnExcluir.setToolTipText("Excluir");
        btnExcluir.setPreferredSize(new java.awt.Dimension(65, 65));

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/salve64.png"))); // NOI18N
        btnSalvar.setToolTipText("Salvar");
        btnSalvar.setPreferredSize(new java.awt.Dimension(65, 65));
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lblPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 684, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblLogim, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(33, 33, 33)
                                                .addComponent(lblNome, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(lblSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblDatanascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtDatanascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNome)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(52, 52, 52)
                                                .addComponent(lblPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(16, 16, 16)
                                                .addComponent(lblTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbxPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(rdbAtivo, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(rdbInativo, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 42, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(67, 67, 67)
                                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(55, 55, 55)
                                .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(59, 59, 59)))))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(lblPesquisar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNome)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLogim)
                    .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSenha)
                    .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPerfil)
                    .addComponent(cbxPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDatanascimento)
                    .addComponent(txtDatanascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTelefone)
                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStatus)
                    .addComponent(rdbAtivo)
                    .addComponent(rdbInativo))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(814, 507));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        // TODO add your handling code here:
        pesquisarUsuario();
    }//GEN-LAST:event_txtPesquisarKeyReleased

    private void tblPesquisarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPesquisarMouseClicked
        // TODO add your handling code here:
        carregarCampos();
    }//GEN-LAST:event_tblPesquisarMouseClicked

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        habilitar();
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

    private void btnIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluirActionPerformed
        // TODO add your handling code here:
        habilitar();
        controle = 1;
    }//GEN-LAST:event_btnIncluirActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
        Desabilitar();
    }//GEN-LAST:event_formWindowActivated

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
            java.util.logging.Logger.getLogger(Tela_Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tela_Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tela_Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tela_Usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Tela_Usuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btgStatus;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnIncluir;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> cbxPerfil;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblDatanascimento;
    private javax.swing.JLabel lblLogim;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblPerfil;
    private javax.swing.JLabel lblPesquisar;
    private javax.swing.JLabel lblSenha;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTelefone;
    private javax.swing.JRadioButton rdbAtivo;
    private javax.swing.JRadioButton rdbInativo;
    private javax.swing.JTable tblPesquisar;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDatanascimento;
    private javax.swing.JTextField txtLogin;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtPesquisar;
    private javax.swing.JTextField txtSenha;
    private javax.swing.JTextField txtStatus;
    private javax.swing.JTextField txtTelefone;
    // End of variables declaration//GEN-END:variables
}
