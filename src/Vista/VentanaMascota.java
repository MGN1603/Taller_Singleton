
package Vista;

import DAO.MascotaDao;
import DTOs.MascotaDTO;
import Excepciones.DatoInvalidoException;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

//ventana tipo swing donde se simula si el crud funciona correctamente.
public class VentanaMascota extends JFrame{
    
    
      private JTextField txtNombre, txtEspecie, txtEdad;
    private JTable tablaMascotas;
    private DefaultTableModel modeloTabla;
    private MascotaDao dao = new MascotaDao();
    private int indiceSeleccionado = -1;

    public VentanaMascota() {
        setTitle("Gestión de Mascotas");
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2));
        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Especie:"));
        txtEspecie = new JTextField();
        panelFormulario.add(txtEspecie);

        panelFormulario.add(new JLabel("Edad:"));
        txtEdad = new JTextField();
        panelFormulario.add(txtEdad);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnActualizar = new JButton("Actualizar");
        panelFormulario.add(btnGuardar);
        panelFormulario.add(btnActualizar);

        add(panelFormulario, BorderLayout.NORTH);

        // Tabla
        modeloTabla = new DefaultTableModel(new Object[]{"Nombre", "Especie", "Edad"}, 0);
        tablaMascotas = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaMascotas);
        add(scroll, BorderLayout.CENTER);

        // Panel inferior
        JPanel panelBotones = new JPanel();
        JButton btnEliminar = new JButton("Eliminar");
        panelBotones.add(btnEliminar);
        add(panelBotones, BorderLayout.SOUTH);

        // Eventos
        btnGuardar.addActionListener(this::guardarMascota);
        btnActualizar.addActionListener(this::actualizarMascota);
        btnEliminar.addActionListener(this::eliminarMascota);
        tablaMascotas.getSelectionModel().addListSelectionListener(e -> seleccionarMascota());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        listarMascotas();
    }

    private void guardarMascota(ActionEvent e) {
        try {
            MascotaDTO mascota = obtenerDatosFormulario();
            dao.guardar(mascota);
            JOptionPane.showMessageDialog(this, "✅ Mascota guardada");
            limpiarCampos();
            listarMascotas();
        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void actualizarMascota(ActionEvent e) {
        try {
            if (indiceSeleccionado < 0) {
                throw new Exception("Seleccione una mascota de la tabla");
            }
            MascotaDTO mascota = obtenerDatosFormulario();
            dao.actualizar(indiceSeleccionado, mascota);
            JOptionPane.showMessageDialog(null, "✏️ Mascota actualizada");
            limpiarCampos();
            listarMascotas();
        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void eliminarMascota(ActionEvent e) {
        try {
            if (indiceSeleccionado < 0) {
                throw new Exception("Seleccione una mascota para eliminar");
            }
            dao.eliminar(indiceSeleccionado);
            JOptionPane.showMessageDialog(null, "🗑️ Mascota eliminada");
            limpiarCampos();
            listarMascotas();
        } catch (Exception ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void seleccionarMascota() {
        indiceSeleccionado = tablaMascotas.getSelectedRow();
        if (indiceSeleccionado >= 0) {
            txtNombre.setText((String) modeloTabla.getValueAt(indiceSeleccionado, 0));
            txtEspecie.setText((String) modeloTabla.getValueAt(indiceSeleccionado, 1));
            txtEdad.setText(String.valueOf(modeloTabla.getValueAt(indiceSeleccionado, 2)));
        }
    }

    private void listarMascotas() {
        modeloTabla.setRowCount(0);
        List<MascotaDTO> lista = dao.listar();
        for (MascotaDTO m : lista) {
            modeloTabla.addRow(new Object[]{m.getNombre(), m.getEspecie(), m.getEdad()});
        }
        indiceSeleccionado = -1;
    }

    private MascotaDTO obtenerDatosFormulario() throws DatoInvalidoException {
        String nombre = txtNombre.getText().trim();
        String especie = txtEspecie.getText().trim();
        String edadStr = txtEdad.getText().trim();

        if (nombre.isEmpty() || especie.isEmpty() || edadStr.isEmpty()) {
            throw new DatoInvalidoException("Todos los campos son obligatorios");
        }

        int edad;
        try {
            edad = Integer.parseInt(edadStr);
        } catch (NumberFormatException e) {
            throw new DatoInvalidoException("La edad debe ser un número válido");
        }

        if (edad < 0) {
            throw new DatoInvalidoException("La edad no puede ser negativa");
        }

        return new MascotaDTO(nombre, especie, edad);
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtEspecie.setText("");
        txtEdad.setText("");
        tablaMascotas.clearSelection();
        indiceSeleccionado = -1;
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    

