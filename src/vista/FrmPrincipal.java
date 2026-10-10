/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;
import Dao.*;
import modelo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import util.ExportadoReportes;
import Dao.ReporteDAO;
import java.io.File;
/**
 *
 * @author keneth-gonzalez
 */
public class FrmPrincipal extends JFrame {
    
 private JTabbedPane tabbedPane;

    // Paneles
    private JPanel panelClientes;
    private JPanel panelMecanicos;
    private JPanel panelRepuestosServicios;
    private JPanel panelOrdenes;
    private JPanel panelPagos;
    private JPanel panelReportes;

    // DAOs
    private ClienteDao clienteDao = new ClienteDao();
    private VehiculoDao vehiculoDao = new VehiculoDao();
    private MecanicoDAO mecanicoDao = new MecanicoDAO();
    private RepuestoDAO repuestoDao = new RepuestoDAO();
    private ServicioDAO servicioDao = new ServicioDAO();
    private OrdenTrabajoDAO ordenDao = new OrdenTrabajoDAO();
    private PagoDAO pagoDao = new PagoDAO();
    private ReporteDAO reporteDao = new ReporteDAO();

    // Componentes Pestaña 1: Clientes y Vehículos
    private JTextField txtClienteId, txtClienteNombre, txtClienteTel, txtClienteCorreo;
    private JTextField txtVehiculoPlaca, txtVehiculoMarca, txtVehiculoModelo, txtVehiculoAnio;
    private JTable tblClientes, tblVehiculos;
    private DefaultTableModel modelClientes, modelVehiculos;

    // Componentes Pestaña 2: Mecánicos
    private JTextField txtMecanicoId, txtMecanicoNombre, txtMecanicoTel, txtMecanicoEsp;
    private JTable tblMecanicos;
    private DefaultTableModel modelMecanicos;

    // Componentes Pestaña 3: Repuestos y Servicios
    private JTextField txtRepuestoId, txtRepuestoNombre, txtRepuestoPrecio, txtRepuestoStock;
    private JTextField txtServicioId, txtServicioNombre, txtServicioPrecio;
    private JTable tblRepuestos, tblServicios;
    private DefaultTableModel modelRepuestos, modelServicios;

    // Componentes Pestaña 4: Órdenes de Trabajo
    private JComboBox<String> cbOtCliente, cbOtVehiculo, cbOtMecanico, cbOtServicio, cbOtRepuesto;
    private JTextField txtOtCantidadRepuesto, txtOtNumOrden;
    private DefaultTableModel modelOtDetalle;
    private JTable tblOtDetalle;

    // Componentes Pestaña 5: Pagos
    private JComboBox<String> cbPagoOrden, cbMetodoPago;
    private JTextField txtPagoMonto;

    // Componentes Pestaña 6: Reportes
    private JLabel lblTotalIngresos;
    private DefaultTableModel modelReporteMecanicos, modelReporteServicios;
    private JTable tblReporteMecanicos, tblReporteServicios;

    public FrmPrincipal() {
        setTitle("Sistema de Gestión de Taller Automotriz");
        setSize(1050, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();

        // 1. Clientes y Vehículos
        initPanelClientes();
        tabbedPane.addTab("1. Clientes y Vehículos", panelClientes);

        // 2. Mecánicos
        initPanelMecanicos();
        tabbedPane.addTab("2. Mecánicos", panelMecanicos);

        // 3. Repuestos y Servicios
        initPanelRepuestosServicios();
        tabbedPane.addTab("3. Repuestos y Servicios", panelRepuestosServicios);

        // 4. Órdenes de Trabajo
        initPanelOrdenes();
        tabbedPane.addTab("4. Órdenes de Trabajo", panelOrdenes);

        // 5. Pagos
        initPanelPagos();
        tabbedPane.addTab("5. Pagos y Cobros", panelPagos);

        // 6. Reportes
        initPanelReportes();
        tabbedPane.addTab("6. Reportes", panelReportes);

        add(tabbedPane);
    }

    // --- Pestaña 1: Clientes y Vehículos ---
    private void initPanelClientes() {
        panelClientes = new JPanel(new GridLayout(1, 2, 10, 10));

        // IZQUIERDA: CLIENTES
        JPanel pnlLeft = new JPanel(new BorderLayout(5, 5));
        pnlLeft.setBorder(BorderFactory.createTitledBorder("Gestión de Clientes"));

        JPanel pnlFormCliente = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlFormCliente.add(new JLabel(" ID Cliente:"));
        txtClienteId = new JTextField();
        pnlFormCliente.add(txtClienteId);

        pnlFormCliente.add(new JLabel(" Nombre:"));
        txtClienteNombre = new JTextField();
        pnlFormCliente.add(txtClienteNombre);

        pnlFormCliente.add(new JLabel(" Teléfono:"));
        txtClienteTel = new JTextField();
        pnlFormCliente.add(txtClienteTel);

        pnlFormCliente.add(new JLabel(" Correo:"));
        txtClienteCorreo = new JTextField();
        pnlFormCliente.add(txtClienteCorreo);

        JPanel pnlBtnsCliente = new JPanel();
        JButton btnGuardarC = new JButton("Guardar");
        JButton btnEliminarC = new JButton("Eliminar");
        JButton btnLimpiarC = new JButton("Limpiar");
        pnlBtnsCliente.add(btnGuardarC);
        pnlBtnsCliente.add(btnEliminarC);
        pnlBtnsCliente.add(btnLimpiarC);

        JPanel pnlNorthCliente = new JPanel(new BorderLayout());
        pnlNorthCliente.add(pnlFormCliente, BorderLayout.CENTER);
        pnlNorthCliente.add(pnlBtnsCliente, BorderLayout.SOUTH);

        modelClientes = new DefaultTableModel(new String[]{"ID", "Nombre", "Teléfono", "Correo"}, 0);
        tblClientes = new JTable(modelClientes);

        pnlLeft.add(pnlNorthCliente, BorderLayout.NORTH);
        pnlLeft.add(new JScrollPane(tblClientes), BorderLayout.CENTER);

        // DERECHA: VEHÍCULOS
        JPanel pnlRight = new JPanel(new BorderLayout(5, 5));
        pnlRight.setBorder(BorderFactory.createTitledBorder("Vehículos del Cliente Seleccionado"));

        JPanel pnlFormVehiculo = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlFormVehiculo.add(new JLabel(" Placa:"));
        txtVehiculoPlaca = new JTextField();
        pnlFormVehiculo.add(txtVehiculoPlaca);

        pnlFormVehiculo.add(new JLabel(" Marca:"));
        txtVehiculoMarca = new JTextField();
        pnlFormVehiculo.add(txtVehiculoMarca);

        pnlFormVehiculo.add(new JLabel(" Modelo:"));
        txtVehiculoModelo = new JTextField();
        pnlFormVehiculo.add(txtVehiculoModelo);

        pnlFormVehiculo.add(new JLabel(" Año:"));
        txtVehiculoAnio = new JTextField();
        pnlFormVehiculo.add(txtVehiculoAnio);

        JPanel pnlBtnsVehiculo = new JPanel();
        JButton btnGuardarV = new JButton("Asignar Vehículo");
        JButton btnEliminarV = new JButton("Eliminar Vehículo");
        pnlBtnsVehiculo.add(btnGuardarV);
        pnlBtnsVehiculo.add(btnEliminarV);

        JPanel pnlNorthVehiculo = new JPanel(new BorderLayout());
        pnlNorthVehiculo.add(pnlFormVehiculo, BorderLayout.CENTER);
        pnlNorthVehiculo.add(pnlBtnsVehiculo, BorderLayout.SOUTH);

        modelVehiculos = new DefaultTableModel(new String[]{"Placa", "Marca", "Modelo", "Año"}, 0);
        tblVehiculos = new JTable(modelVehiculos);

        pnlRight.add(pnlNorthVehiculo, BorderLayout.NORTH);
        pnlRight.add(new JScrollPane(tblVehiculos), BorderLayout.CENTER);

        panelClientes.add(pnlLeft);
        panelClientes.add(pnlRight);

        // EVENTOS
        btnGuardarC.addActionListener(e -> guardarCliente());
        btnEliminarC.addActionListener(e -> eliminarCliente());
        btnLimpiarC.addActionListener(e -> limpiarFormCliente());
        btnGuardarV.addActionListener(e -> guardarVehiculo());
        btnEliminarV.addActionListener(e -> eliminarVehiculo());

        tblClientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblClientes.getSelectedRow() != -1) {
                cargarVehiculosDeCliente();
            }
        });

        cargarTablaClientes();
    }

    private void cargarTablaClientes() {
        modelClientes.setRowCount(0);
        try {
            List<Cliente> lista = clienteDao.listar();
            for (Cliente c : lista) {
                modelClientes.addRow(new Object[]{c.getIdCliente(), c.getNombre(), c.getTelefono(), c.getCorreo()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar clientes: " + ex.getMessage());
        }
    }

    private void cargarVehiculosDeCliente() {
        int fila = tblClientes.getSelectedRow();
        if (fila == -1) return;
        int idCliente = Integer.parseInt(tblClientes.getValueAt(fila, 0).toString());
        modelVehiculos.setRowCount(0);
        try {
            List<Vehiculo> lista = vehiculoDao.listarPorCliente(idCliente);
            for (Vehiculo v : lista) {
                modelVehiculos.addRow(new Object[]{v.getPlaca(), v.getMarca(), v.getModelo(), v.getAnio()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar vehículos: " + ex.getMessage());
        }
    }

    private void guardarCliente() {
        try {
            Cliente c = new Cliente();
            c.setIdCliente(Integer.parseInt(txtClienteId.getText().trim()));
            c.setNombre(txtClienteNombre.getText().trim());
            c.setTelefono(txtClienteTel.getText().trim());
            c.setCorreo(txtClienteCorreo.getText().trim());

            if (clienteDao.existeCliente(c.getIdCliente())) {
                clienteDao.actualizar(c);
                JOptionPane.showMessageDialog(this, "Cliente actualizado exitosamente.");
            } else {
                clienteDao.registrar(c);
                JOptionPane.showMessageDialog(this, "Cliente registrado exitosamente.");
            }
            limpiarFormCliente();
            cargarTablaClientes();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCliente() {
        int fila = tblClientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente de la tabla.");
            return;
        }
        int idCliente = Integer.parseInt(tblClientes.getValueAt(fila, 0).toString());
        try {
            if (clienteDao.eliminar(idCliente)) {
                JOptionPane.showMessageDialog(this, "Cliente eliminado.");
                cargarTablaClientes();
                modelVehiculos.setRowCount(0);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Restricción de Negocio", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void guardarVehiculo() {
        int fila = tblClientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente para asociarle el vehículo.");
            return;
        }
        int idCliente = Integer.parseInt(tblClientes.getValueAt(fila, 0).toString());

        try {
            Vehiculo v = new Vehiculo();
            v.setPlaca(txtVehiculoPlaca.getText().trim());
            v.setMarca(txtVehiculoMarca.getText().trim());
            v.setModelo(txtVehiculoModelo.getText().trim());
            v.setAnio(Integer.parseInt(txtVehiculoAnio.getText().trim()));
            v.setIdCliente(idCliente);

            if (vehiculoDao.registrar(v)) {
                JOptionPane.showMessageDialog(this, "Vehículo asociado correctamente.");
                cargarVehiculosDeCliente();
                txtVehiculoPlaca.setText("");
                txtVehiculoMarca.setText("");
                txtVehiculoModelo.setText("");
                txtVehiculoAnio.setText("");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarVehiculo() {
        int fila = tblVehiculos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un vehículo.");
            return;
        }
        String placa = tblVehiculos.getValueAt(fila, 0).toString();
        try {
            if (vehiculoDao.eliminar(placa)) {
                JOptionPane.showMessageDialog(this, "Vehículo eliminado.");
                cargarVehiculosDeCliente();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Restricción de Negocio", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void limpiarFormCliente() {
        txtClienteId.setText("");
        txtClienteNombre.setText("");
        txtClienteTel.setText("");
        txtClienteCorreo.setText("");
    }

    // --- Pestaña 2: Mecánicos ---
    private void initPanelMecanicos() {
        panelMecanicos = new JPanel(new BorderLayout(5, 5));
        JLabel lblTitulo = new JLabel("Gestión de Mecánicos del Taller", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelMecanicos.add(lblTitulo, BorderLayout.NORTH);

        JPanel pnlForm = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlForm.add(new JLabel(" ID Mecánico:"));
        txtMecanicoId = new JTextField();
        pnlForm.add(txtMecanicoId);

        pnlForm.add(new JLabel(" Nombre:"));
        txtMecanicoNombre = new JTextField();
        pnlForm.add(txtMecanicoNombre);

        pnlForm.add(new JLabel(" Teléfono:"));
        txtMecanicoTel = new JTextField();
        pnlForm.add(txtMecanicoTel);

        pnlForm.add(new JLabel(" Especialidad:"));
        txtMecanicoEsp = new JTextField();
        pnlForm.add(txtMecanicoEsp);

        modelMecanicos = new DefaultTableModel(new String[]{"ID", "Nombre", "Teléfono", "Especialidad"}, 0);
        tblMecanicos = new JTable(modelMecanicos);

        JPanel pnlCenter = new JPanel(new BorderLayout(5, 5));
        pnlCenter.add(pnlForm, BorderLayout.NORTH);
        pnlCenter.add(new JScrollPane(tblMecanicos), BorderLayout.CENTER);

        panelMecanicos.add(pnlCenter, BorderLayout.CENTER);
    }

    // --- Pestaña 3: Repuestos y Servicios ---
    private void initPanelRepuestosServicios() {
        panelRepuestosServicios = new JPanel(new GridLayout(1, 2, 10, 10));

        // REPUESTOS
        JPanel pnlRep = new JPanel(new BorderLayout(5, 5));
        pnlRep.setBorder(BorderFactory.createTitledBorder("Gestión de Repuestos"));

        JPanel pnlFormRep = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlFormRep.add(new JLabel(" ID Repuesto:"));
        txtRepuestoId = new JTextField();
        pnlFormRep.add(txtRepuestoId);

        pnlFormRep.add(new JLabel(" Nombre:"));
        txtRepuestoNombre = new JTextField();
        pnlFormRep.add(txtRepuestoNombre);

        pnlFormRep.add(new JLabel(" Precio Unitario:"));
        txtRepuestoPrecio = new JTextField();
        pnlFormRep.add(txtRepuestoPrecio);

        pnlFormRep.add(new JLabel(" Stock Inicial:"));
        txtRepuestoStock = new JTextField();
        pnlFormRep.add(txtRepuestoStock);

        JPanel pnlBtnsRep = new JPanel();
        JButton btnGuardarR = new JButton("Guardar");
        JButton btnEliminarR = new JButton("Eliminar");
        JButton btnLimpiarR = new JButton("Limpiar");
        pnlBtnsRep.add(btnGuardarR);
        pnlBtnsRep.add(btnEliminarR);
        pnlBtnsRep.add(btnLimpiarR);

        JPanel pnlNorthRep = new JPanel(new BorderLayout());
        pnlNorthRep.add(pnlFormRep, BorderLayout.CENTER);
        pnlNorthRep.add(pnlBtnsRep, BorderLayout.SOUTH);

        modelRepuestos = new DefaultTableModel(new String[]{"ID", "Nombre", "Precio", "Stock"}, 0);
        tblRepuestos = new JTable(modelRepuestos);

        pnlRep.add(pnlNorthRep, BorderLayout.NORTH);
        pnlRep.add(new JScrollPane(tblRepuestos), BorderLayout.CENTER);

        // SERVICIOS
        JPanel pnlServ = new JPanel(new BorderLayout(5, 5));
        pnlServ.setBorder(BorderFactory.createTitledBorder("Catálogo de Servicios"));

        JPanel pnlFormServ = new JPanel(new GridLayout(3, 2, 5, 5));
        pnlFormServ.add(new JLabel(" ID Servicio:"));
        txtServicioId = new JTextField();
        pnlFormServ.add(txtServicioId);

        pnlFormServ.add(new JLabel(" Descripción/Nombre:"));
        txtServicioNombre = new JTextField();
        pnlFormServ.add(txtServicioNombre);

        pnlFormServ.add(new JLabel(" Precio Servicio:"));
        txtServicioPrecio = new JTextField();
        pnlFormServ.add(txtServicioPrecio);

        JPanel pnlBtnsServ = new JPanel();
        JButton btnGuardarS = new JButton("Guardar");
        JButton btnEliminarS = new JButton("Eliminar");
        JButton btnLimpiarS = new JButton("Limpiar");
        pnlBtnsServ.add(btnGuardarS);
        pnlBtnsServ.add(btnEliminarS);
        pnlBtnsServ.add(btnLimpiarS);

        JPanel pnlNorthServ = new JPanel(new BorderLayout());
        pnlNorthServ.add(pnlFormServ, BorderLayout.CENTER);
        pnlNorthServ.add(pnlBtnsServ, BorderLayout.SOUTH);

        modelServicios = new DefaultTableModel(new String[]{"ID", "Descripción", "Precio"}, 0);
        tblServicios = new JTable(modelServicios);

        pnlServ.add(pnlNorthServ, BorderLayout.NORTH);
        pnlServ.add(new JScrollPane(tblServicios), BorderLayout.CENTER);

        panelRepuestosServicios.add(pnlRep);
        panelRepuestosServicios.add(pnlServ);

        // EVENTOS
        btnGuardarR.addActionListener(e -> guardarRepuesto());
        btnEliminarR.addActionListener(e -> eliminarRepuesto());
        btnLimpiarR.addActionListener(e -> limpiarFormRepuesto());

        btnGuardarS.addActionListener(e -> guardarServicio());
        btnEliminarS.addActionListener(e -> eliminarServicio());
        btnLimpiarS.addActionListener(e -> limpiarFormServicio());

        cargarTablaRepuestos();
        cargarTablaServicios();
    }

    private void cargarTablaRepuestos() {
        modelRepuestos.setRowCount(0);
        try {
            List<Repuesto> lista = repuestoDao.listar();
            for (Repuesto r : lista) {
                modelRepuestos.addRow(new Object[]{r.getIdRepuesto(), r.getNombre(), r.getPrecio(), r.getStock()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar repuestos: " + ex.getMessage());
        }
    }

    private void guardarRepuesto() {
        try {
            Repuesto r = new Repuesto();
            r.setIdRepuesto(Integer.parseInt(txtRepuestoId.getText().trim()));
            r.setNombre(txtRepuestoNombre.getText().trim());
            r.setPrecio(Double.parseDouble(txtRepuestoPrecio.getText().trim()));
            r.setStock(Integer.parseInt(txtRepuestoStock.getText().trim()));

            if (repuestoDao.existeRepuesto(r.getIdRepuesto())) {
                repuestoDao.actualizar(r);
                JOptionPane.showMessageDialog(this, "Repuesto actualizado.");
            } else {
                repuestoDao.registrar(r);
                JOptionPane.showMessageDialog(this, "Repuesto registrado.");
            }
            limpiarFormRepuesto();
            cargarTablaRepuestos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarRepuesto() {
        int fila = tblRepuestos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un repuesto.");
            return;
        }
        int id = Integer.parseInt(tblRepuestos.getValueAt(fila, 0).toString());
        try {
            if (repuestoDao.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "Repuesto eliminado.");
                cargarTablaRepuestos();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormRepuesto() {
        txtRepuestoId.setText("");
        txtRepuestoNombre.setText("");
        txtRepuestoPrecio.setText("");
        txtRepuestoStock.setText("");
    }

    private void cargarTablaServicios() {
        modelServicios.setRowCount(0);
        try {
            List<Servicio> lista = servicioDao.listar();
            for (Servicio s : lista) {
                modelServicios.addRow(new Object[]{s.getIdServicio(), s.getNombre(), s.getPrecio()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar servicios: " + ex.getMessage());
        }
    }

    private void guardarServicio() {
        try {
            Servicio s = new Servicio();
            s.setIdServicio(Integer.parseInt(txtServicioId.getText().trim()));
            s.setNombre(txtServicioNombre.getText().trim());
            s.setPrecio(Double.parseDouble(txtServicioPrecio.getText().trim()));

            if (servicioDao.existeServicio(s.getIdServicio())) {
                servicioDao.actualizar(s);
                JOptionPane.showMessageDialog(this, "Servicio actualizado.");
            } else {
                servicioDao.registrar(s);
                JOptionPane.showMessageDialog(this, "Servicio registrado.");
            }
            limpiarFormServicio();
            cargarTablaServicios();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarServicio() {
        int fila = tblServicios.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio.");
            return;
        }
        int id = Integer.parseInt(tblServicios.getValueAt(fila, 0).toString());
        try {
            if (servicioDao.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "Servicio eliminado.");
                cargarTablaServicios();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormServicio() {
        txtServicioId.setText("");
        txtServicioNombre.setText("");
        txtServicioPrecio.setText("");
    }

    // --- Pestaña 4: Órdenes de Trabajo ---
    private void initPanelOrdenes() {
        panelOrdenes = new JPanel(new BorderLayout(10, 10));

        JPanel pnlNuevaOrden = new JPanel(new GridLayout(2, 4, 5, 5));
        pnlNuevaOrden.setBorder(BorderFactory.createTitledBorder("1. Apertura de Orden de Trabajo"));

        pnlNuevaOrden.add(new JLabel(" Seleccionar Cliente:"));
        cbOtCliente = new JComboBox<>();
        pnlNuevaOrden.add(cbOtCliente);

        pnlNuevaOrden.add(new JLabel(" Seleccionar Vehículo:"));
        cbOtVehiculo = new JComboBox<>();
        pnlNuevaOrden.add(cbOtVehiculo);

        pnlNuevaOrden.add(new JLabel(" Seleccionar Mecánico:"));
        cbOtMecanico = new JComboBox<>();
        pnlNuevaOrden.add(cbOtMecanico);

        JButton btnCrearOrden = new JButton("Crear Orden");
        pnlNuevaOrden.add(btnCrearOrden);

        txtOtNumOrden = new JTextField();
        txtOtNumOrden.setEditable(false);
        pnlNuevaOrden.add(txtOtNumOrden);

        JPanel pnlDetalle = new JPanel(new GridLayout(2, 1, 5, 5));
        pnlDetalle.setBorder(BorderFactory.createTitledBorder("2. Detalle de Trabajos y Repuestos"));

        JPanel pnlServicioLine = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cbOtServicio = new JComboBox<>();
        JButton btnAgregarServ = new JButton("Agregar Servicio");
        pnlServicioLine.add(new JLabel("Servicio: "));
        pnlServicioLine.add(cbOtServicio);
        pnlServicioLine.add(btnAgregarServ);

        JPanel pnlRepuestoLine = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cbOtRepuesto = new JComboBox<>();
        txtOtCantidadRepuesto = new JTextField("1", 5);
        JButton btnAgregarRep = new JButton("Agregar Repuesto");
        pnlRepuestoLine.add(new JLabel("Repuesto: "));
        pnlRepuestoLine.add(cbOtRepuesto);
        pnlRepuestoLine.add(new JLabel("Cantidad: "));
        pnlRepuestoLine.add(txtOtCantidadRepuesto);
        pnlRepuestoLine.add(btnAgregarRep);

        pnlDetalle.add(pnlServicioLine);
        pnlDetalle.add(pnlRepuestoLine);

        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.add(pnlNuevaOrden, BorderLayout.NORTH);
        pnlTop.add(pnlDetalle, BorderLayout.SOUTH);

        modelOtDetalle = new DefaultTableModel(new String[]{"Tipo", "Descripción", "Cantidad", "Precio Unitario", "Subtotal"}, 0);
        tblOtDetalle = new JTable(modelOtDetalle);

        panelOrdenes.add(pnlTop, BorderLayout.NORTH);
        panelOrdenes.add(new JScrollPane(tblOtDetalle), BorderLayout.CENTER);

        // EVENTOS
        cbOtCliente.addActionListener(e -> cargarVehiculosCombo());
        btnCrearOrden.addActionListener(e -> crearOrden());
        btnAgregarServ.addActionListener(e -> agregarServicio());
        btnAgregarRep.addActionListener(e -> agregarRepuesto());

        cargarCombosOrden();
    }

    private void cargarCombosOrden() {
        try {
            cbOtCliente.removeAllItems();
            for (Cliente c : clienteDao.listar()) {
                cbOtCliente.addItem(c.getIdCliente() + " - " + c.getNombre());
            }

            cbOtMecanico.removeAllItems();
            for (Mecanico m : mecanicoDao.listar()) {
                cbOtMecanico.addItem(m.getIdMecanico() + " - " + m.getNombre());
            }

            cbOtServicio.removeAllItems();
            for (Servicio s : servicioDao.listar()) {
                cbOtServicio.addItem(s.getIdServicio() + " - " + s.getNombre() + " ($" + s.getPrecio() + ")");
            }

            cbOtRepuesto.removeAllItems();
            for (Repuesto r : repuestoDao.listar()) {
                cbOtRepuesto.addItem(r.getIdRepuesto() + " - " + r.getNombre() + " ($" + r.getPrecio() + ")");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos en los combos: " + ex.getMessage());
        }
    }

    private void cargarVehiculosCombo() {
        cbOtVehiculo.removeAllItems();
        if (cbOtCliente.getSelectedItem() == null) return;

        String itemSel = cbOtCliente.getSelectedItem().toString();
        int idCliente = Integer.parseInt(itemSel.split(" - ")[0]);

        try {
            for (Vehiculo v : vehiculoDao.listarPorCliente(idCliente)) {
                cbOtVehiculo.addItem(v.getPlaca() + " - " + v.getMarca() + " " + v.getModelo());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar vehículos: " + ex.getMessage());
        }
    }

    private void crearOrden() {
        try {
            if (cbOtCliente.getSelectedItem() == null || cbOtVehiculo.getSelectedItem() == null || cbOtMecanico.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar Cliente, Vehículo y Mecánico.");
                return;
            }

            int idCliente = Integer.parseInt(cbOtCliente.getSelectedItem().toString().split(" - ")[0]);
            String placa = cbOtVehiculo.getSelectedItem().toString().split(" - ")[0];
            int idMecanico = Integer.parseInt(cbOtMecanico.getSelectedItem().toString().split(" - ")[0]);

            OrdenTrabajo ot = new OrdenTrabajo();
            ot.setIdCliente(idCliente);
            ot.setPlaca(placa);
            ot.setIdMecanico(idMecanico);

            int idGenerado = ordenDao.registrarOrden(ot);
            if (idGenerado > 0) {
                txtOtNumOrden.setText("Orden Activa #" + idGenerado);
                modelOtDetalle.setRowCount(0);
                JOptionPane.showMessageDialog(this, "Orden de Trabajo #" + idGenerado + " creada correctamente.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al crear la orden: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarServicio() {
        if (txtOtNumOrden.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Primero debe crear una orden.");
            return;
        }
        int idOrden = Integer.parseInt(txtOtNumOrden.getText().replace("Orden Activa #", ""));
        String itemServ = cbOtServicio.getSelectedItem().toString();
        int idServicio = Integer.parseInt(itemServ.split(" - ")[0]);

        try {
            List<Servicio> lista = servicioDao.listar();
            for (Servicio s : lista) {
                if (s.getIdServicio() == idServicio) {
                    ordenDao.agregarServicioAOrden(idOrden, idServicio, s.getPrecio());
                    ordenDao.recalcularTotalOrden(idOrden);
                    modelOtDetalle.addRow(new Object[]{"Servicio", s.getNombre(), 1, s.getPrecio(), s.getPrecio()});
                    break;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar servicio: " + ex.getMessage());
        }
    }

    private void agregarRepuesto() {
        if (txtOtNumOrden.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Primero debe crear una orden.");
            return;
        }
        int idOrden = Integer.parseInt(txtOtNumOrden.getText().replace("Orden Activa #", ""));
        String itemRep = cbOtRepuesto.getSelectedItem().toString();
        int idRepuesto = Integer.parseInt(itemRep.split(" - ")[0]);
        int cantidad = Integer.parseInt(txtOtCantidadRepuesto.getText().trim());

        try {
            List<Repuesto> lista = repuestoDao.listar();
            for (Repuesto r : lista) {
                if (r.getIdRepuesto() == idRepuesto) {
                    ordenDao.agregarRepuestoAOrden(idOrden, idRepuesto, cantidad, r.getPrecio());
                    ordenDao.recalcularTotalOrden(idOrden);
                    modelOtDetalle.addRow(new Object[]{"Repuesto", r.getNombre(), cantidad, r.getPrecio(), cantidad * r.getPrecio()});
                    break;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar repuesto: " + ex.getMessage());
        }
    }

    // --- Pestaña 5: Pagos y Cobros ---
    private void initPanelPagos() {
        panelPagos = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JPanel pnlFormPago = new JPanel(new GridLayout(4, 2, 10, 10));
        pnlFormPago.setBorder(BorderFactory.createTitledBorder("Procesar Pago de Orden"));

        pnlFormPago.add(new JLabel(" Seleccionar Orden Pendiente:"));
        cbPagoOrden = new JComboBox<>();
        pnlFormPago.add(cbPagoOrden);

        pnlFormPago.add(new JLabel(" Método de Pago:"));
        cbMetodoPago = new JComboBox<>(new String[]{"EFECTIVO", "TARJETA", "TRANSFERENCIA"});
        pnlFormPago.add(cbMetodoPago);

        pnlFormPago.add(new JLabel(" Monto a Pagar:"));
        txtPagoMonto = new JTextField();
        pnlFormPago.add(txtPagoMonto);

        JButton btnProcesarPago = new JButton("Procesar y Facturar");
        pnlFormPago.add(new JLabel(""));
        pnlFormPago.add(btnProcesarPago);

        panelPagos.add(pnlFormPago);

        // EVENTOS
        cbPagoOrden.addActionListener(e -> {
            if (cbPagoOrden.getSelectedItem() != null) {
                String item = cbPagoOrden.getSelectedItem().toString();
                String total = item.split("Total: \\$")[1];
                txtPagoMonto.setText(total);
            }
        });

        btnProcesarPago.addActionListener(e -> procesarPago());

        cargarOrdenesPendientesCombo();
    }

    private void cargarOrdenesPendientesCombo() {
        cbPagoOrden.removeAllItems();
        try {
            List<String> pendientes = pagoDao.listarOrdenesPendientes();
            for (String ord : pendientes) {
                cbPagoOrden.addItem(ord);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar órdenes pendientes: " + ex.getMessage());
        }
    }

    private void procesarPago() {
        if (cbPagoOrden.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "No hay órdenes pendientes seleccionadas.");
            return;
        }

        try {
            String itemStr = cbPagoOrden.getSelectedItem().toString();
            int idOrden = Integer.parseInt(itemStr.split(" - ")[0].replace("Orden #", ""));
            String metodo = cbMetodoPago.getSelectedItem().toString();
            double monto = Double.parseDouble(txtPagoMonto.getText().trim());

            if (pagoDao.registrarPago(idOrden, metodo, monto)) {
                JOptionPane.showMessageDialog(this, "Pago procesado y facturado con éxito.");
                cargarOrdenesPendientesCombo();
                txtPagoMonto.setText("");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al procesar pago: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- Pestaña 6: Reportes ---
    private void initPanelReportes() {
panelReportes = new JPanel(new BorderLayout(10, 10));

    // Panel superior para la etiqueta y el botón de actualización
    JPanel pnlTop = new JPanel(new BorderLayout());

    lblTotalIngresos = new JLabel("Total Ingresos Generados: $0.00", SwingConstants.CENTER);
    lblTotalIngresos.setFont(new Font("Arial", Font.BOLD, 14));
    pnlTop.add(lblTotalIngresos, BorderLayout.CENTER);

    JButton btnActualizarReportes = new JButton("Actualizar Reportes");
    pnlTop.add(btnActualizarReportes, BorderLayout.EAST);

    modelReporteMecanicos = new DefaultTableModel(new String[]{"Mecánico", "Órdenes Atendidas"}, 0);
    tblReporteMecanicos = new JTable(modelReporteMecanicos);

    modelReporteServicios = new DefaultTableModel(new String[]{"Servicio", "Veces Solicitado"}, 0);
    tblReporteServicios = new JTable(modelReporteServicios);

    JPanel pnlTablas = new JPanel(new GridLayout(1, 2, 10, 10));
    pnlTablas.add(new JScrollPane(tblReporteMecanicos));
    pnlTablas.add(new JScrollPane(tblReporteServicios));

    // --- Panel Inferior con Botones de Exportación ---
    JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
    JButton btnExportarHTML = new JButton("Exportar Reportes a HTML");
    JButton btnExportarExcel = new JButton("Exportar Reportes a Excel");

    pnlBottom.add(btnExportarHTML);
    pnlBottom.add(btnExportarExcel);

    panelReportes.add(pnlTop, BorderLayout.NORTH);
    panelReportes.add(pnlTablas, BorderLayout.CENTER);
    panelReportes.add(pnlBottom, BorderLayout.SOUTH);

    // Evento para recargar datos
    btnActualizarReportes.addActionListener(e -> cargarReportes());

    // Eventos para Exportar
    btnExportarHTML.addActionListener(e -> {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar Reporte en HTML");
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File archivo = new File(chooser.getSelectedFile().getAbsolutePath() + ".html");
            util.ExportadoReportes.exportarTablaAHTML(tblReporteMecanicos, "Reporte de Órdenes por Mecánico", archivo);
        }
    });

    btnExportarExcel.addActionListener(e -> {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar Reporte en Excel (.csv)");
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File archivo = new File(chooser.getSelectedFile().getAbsolutePath() + ".csv");
            util.ExportadoReportes.exportarTablaAExcel(tblReporteMecanicos, archivo);
        }
    });

    cargarReportes();
    }

    private void cargarReportes() {
        try {
            // 1. Cargar Ingresos
            double ingresos = reporteDao.obtenerTotalIngresos();
            lblTotalIngresos.setText(String.format("Total Ingresos Acumulados: $%.2f", ingresos));

            // 2. Cargar Mecánicos
            modelReporteMecanicos.setRowCount(0);
            for (Object[] fila : reporteDao.obtenerOrdenesPorMecanico()) {
                modelReporteMecanicos.addRow(fila);
            }

            // 3. Cargar Servicios
            modelReporteServicios.setRowCount(0);
            for (Object[] fila : reporteDao.obtenerServiciosMasSolicitados()) {
                modelReporteServicios.addRow(fila);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar reportes: " + ex.getMessage());
        }
}

}

