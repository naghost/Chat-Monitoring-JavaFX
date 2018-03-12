package Cliente;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UsuariosModel {
    StringProperty usuario;

    public UsuariosModel(String s) {
        usuario = new SimpleStringProperty(s);
    }

    public String getUsuario() {
        return usuario.get();
    }

    public StringProperty usuarioProperty() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario.set(usuario);
    }
}
