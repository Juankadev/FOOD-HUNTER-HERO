package com.example.ffh_rep.models.repositories;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.ffh_rep.entidades.Articulo;
import com.example.ffh_rep.entidades.Categoria;
import com.example.ffh_rep.entidades.Comercio;
import com.example.ffh_rep.entidades.Marca;
import com.example.ffh_rep.entidades.Stock;
import com.example.ffh_rep.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class ArticuloRepository {
    /**
     * Obtiene la lista de artículos activos asociados a un comercio específico mediante un identificador.
     *
     * @param mlDataArticulos MutableLiveData que se actualizará con la lista de artículos obtenida.
     * @param id Identificador del comercio para el cual se buscan los artículos.
     * @return MutableLiveData actualizado con la lista de artículos o una lista vacía en caso de error o ausencia de datos.
     */
    public MutableLiveData<List<Articulo>> getArticulosByIdComercio(MutableLiveData<List<Articulo>> mlDataArticulos, int id) {
        CompletableFuture.supplyAsync(() -> {
            List<Articulo> lArticulos = new ArrayList<>();
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT a.id_articulo, a.id_comercio, a.descripcion, a.precio, a.id_categoria, a.id_marca, a.imagen, a.estado, s.id_stock, s.cantidad, s.fecha_vencimiento, c.descripcion as categoria, m.descripcion as marca" +
                         " FROM Articulos a " +
                         " inner join Categorias c on c.id_categoria = a.id_categoria" +
                         " inner join Marcas m on m.id_marca = a.id_marca " +
                         " Left join Stocks s on s.id_articulo = a.id_articulo "+
                         "WHERE a.estado = '1' and a.id_comercio ="+id);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Articulo articulo = new Articulo();
                    articulo.setIdArticulo(rs.getInt("id_articulo"));
                    articulo.setDescripcion(rs.getString("descripcion"));
                    articulo.setComercio(new Comercio());
                    articulo.getComercio().setId(rs.getInt("id_comercio"));
                    articulo.setPrecio(rs.getDouble("precio"));
                    articulo.setCategoria(new Categoria());
                    articulo.getCategoria().setIdCategoria(rs.getInt("id_categoria"));
                    articulo.getCategoria().setDescripcion(rs.getString("categoria"));
                    articulo.setMarca(new Marca());
                    articulo.getMarca().setIdMarca(rs.getInt("id_marca"));
                    articulo.getMarca().setDescripcion(rs.getString("marca"));
                    articulo.setStockArticulo(new Stock());
                    articulo.getStockArticulo().setId_stock(rs.getInt("id_stock"));
                    articulo.getStockArticulo().setCantidad(rs.getInt("cantidad"));
                    articulo.setImagen(rs.getString("imagen"));
                    articulo.setEstado(rs.getString("estado"));

                    lArticulos.add(articulo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return lArticulos;
        }).thenAcceptAsync(articulos -> {
            if (articulos != null) {
                mlDataArticulos.postValue(articulos.isEmpty() ? new ArrayList<>() : articulos);
            } else {
                mlDataArticulos.postValue(new ArrayList<>());
            }
        });
        return mlDataArticulos;
    }

    public MutableLiveData<List<Articulo>> getArticulosByIdComercioWithStock(MutableLiveData<List<Articulo>> mlDataArticulos, int id) {
        CompletableFuture.supplyAsync(() -> {
            List<Articulo> lArticulos = new ArrayList<>();
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT a.id_articulo, a.id_comercio, a.descripcion, a.precio, a.id_categoria, a.id_marca, a.imagen, a.estado, s.id_stock, s.cantidad, s.fecha_vencimiento, c.descripcion as categoria, m.descripcion as marca" +
                         " FROM Articulos a inner join Stocks s on s.id_articulo = a.id_articulo" +
                         " inner join Categorias c on c.id_categoria = a.id_categoria" +
                         " inner join Marcas m on m.id_marca = a.id_marca " +
                         "WHERE a.estado = '1' and s.cantidad > 0 and a.id_comercio ="+id);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Articulo articulo = new Articulo();
                    articulo.setIdArticulo(rs.getInt("id_articulo"));
                    articulo.setDescripcion(rs.getString("descripcion"));
                    articulo.setComercio(new Comercio());
                    articulo.getComercio().setId(rs.getInt("id_comercio"));
                    articulo.setPrecio(rs.getDouble("precio"));
                    articulo.setCategoria(new Categoria());
                    articulo.getCategoria().setIdCategoria(rs.getInt("id_categoria"));
                    articulo.getCategoria().setDescripcion(rs.getString("categoria"));
                    articulo.setMarca(new Marca());
                    articulo.getMarca().setIdMarca(rs.getInt("id_marca"));
                    articulo.getMarca().setDescripcion(rs.getString("marca"));
                    articulo.setStockArticulo(new Stock());
                    articulo.getStockArticulo().setId_stock(rs.getInt("id_stock"));
                    articulo.getStockArticulo().setCantidad(rs.getInt("cantidad"));
                    articulo.setImagen(rs.getString("imagen"));
                    articulo.setEstado(rs.getString("estado"));

                    lArticulos.add(articulo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return lArticulos;
        }).thenAcceptAsync(articulos -> {
            if (articulos != null) {
                mlDataArticulos.postValue(articulos.isEmpty() ? new ArrayList<>() : articulos);
            } else {
                mlDataArticulos.postValue(new ArrayList<>());
            }
        });
        return mlDataArticulos;
    }


    /**
     * Obtiene la lista de artículos activos desde la base de datos.
     *
     * @param mlDataArticulos MutableLiveData que se actualizará con la lista de artículos obtenida.
     * @return MutableLiveData actualizado con la lista de artículos activos o una lista vacía en caso de error o ausencia de datos.
     */
    public MutableLiveData<List<Articulo>> getArticulos(MutableLiveData<List<Articulo>> mlDataArticulos) {
        CompletableFuture.supplyAsync(() -> {
            List<Articulo> lArticulos = new ArrayList<>();
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT * FROM Articulos a INNER JOIN Stocks s ON a.id_articulo = s.id_articulo WHERE a.estado = 1 AND s.cantidad > 0");
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Articulo aData = new Articulo();
                    int id = rs.getInt("id_articulo");
                    String descripcion = rs.getString("descripcion");
                    Double precio = rs.getDouble("precio");
                    int id_categoria = rs.getInt("id_categoria");
                    aData.setComercio(new Comercio());
                    aData.getComercio().setId(rs.getInt("id_comercio"));
                    aData.setIdArticulo(id);
                    aData.setDescripcion(descripcion);
                    aData.setPrecio(precio);
                    aData.setCategoria(new Categoria());
                    aData.setImagen(rs.getString("imagen"));
                    aData.getCategoria().setIdCategoria(id_categoria);
                    lArticulos.add(aData);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return lArticulos;
        }).thenAcceptAsync(articulos -> mlDataArticulos.postValue(articulos));
        return mlDataArticulos;
    }
    /**
     * Inserta un nuevo artículo en la base de datos.
     *
     * @param context   Contexto de la aplicación.
     * @param articulo  Artículo a ser insertado en la base de datos.
     */
    public void insertArticulo(Context context, Articulo articulo) {

        CompletableFuture.runAsync(() -> {
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("INSERT INTO Articulos (descripcion, id_comercio, precio, id_categoria, id_marca, id_articulo, imagen, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {

                ps.setString(1, articulo.getDescripcion());
                ps.setInt(2, articulo.getComercio().getId());
                ps.setDouble(3, articulo.getPrecio());
                ps.setInt(4, articulo.getCategoria().getIdCategoria());
                ps.setInt(5, articulo.getMarca().getIdMarca());
                ps.setInt(6, articulo.getIdArticulo());
                ps.setString(7, articulo.getImagen());
                ps.setString(8, articulo.getEstado());


                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {

                    ResultSet keysGenerated = ps.getGeneratedKeys();
                    int idArt = -1;
                    if(keysGenerated.next()){
                        idArt = keysGenerated.getInt(1);
                    }
                    Toast.makeText(context, "Artículo agregado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al insertar el artículo", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Error al insertar el artículo", Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * Actualiza la información de un artículo en la base de datos.
     *
     * @param context  Contexto de la aplicación.
     * @param articulo Objeto Articulo con la información actualizada.
     */
    public void updateArticulo(Context context, Articulo articulo) {
        CompletableFuture.runAsync(() -> {
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("UPDATE Articulos SET descripcion = ?, id_comercio = ?, precio = ?, id_categoria = ?, id_marca = ?, imagen = ?, estado = ? WHERE id_articulo = ?")) {

                ps.setString(1, articulo.getDescripcion());
                ps.setInt(2, articulo.getComercio().getId());
                ps.setDouble(3, articulo.getPrecio());
                ps.setInt(4, articulo.getCategoria().getIdCategoria());
                ps.setInt(5, articulo.getMarca().getIdMarca());
                ps.setString(6, articulo.getImagen());
                ps.setString(7, articulo.getEstado());
                ps.setInt(8, articulo.getIdArticulo());

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    Toast.makeText(context, "Artículo actualizado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al actualizar el artículo", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Error al actualizar el artículo", Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * Busca un artículo en la base de datos por su ID de artículo.
     * Ejemplo de como utilizar esta funcion
     * 
     * buscarArticuloPorID(ID)
     *  .thenAcceptAsync(articulos -> {
     *      if (articulos != null) {
     *          if (articulos.isEmpty()) {
     *              // Manejar el caso en que no se encontraron artículos
     *          } else {
     *              // Manejar la lista de artículos
     *          }
     *      } else {
     *          // Manejar el caso de error
     *      }
     *  });
     * 
     * @param idArticulo ID del artículo a buscar.
     * @return CompletableFuture que contiene el artículo si se encuentra, o null si no existe.
     */
    public CompletableFuture<Articulo> buscarArticuloPorID(int idArticulo) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT * FROM Articulos WHERE id_articulo = ?")) {

                ps.setInt(1, idArticulo);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    Articulo articulo = new Articulo();
                    articulo.setIdArticulo(rs.getInt("id_articulo"));
                    articulo.setDescripcion(rs.getString("descripcion"));
                    articulo.setComercio(new Comercio());
                    articulo.getComercio().setId(rs.getInt("id_comercio"));
                    articulo.setPrecio(rs.getDouble("precio"));
                    articulo.setCategoria(new Categoria());
                    articulo.getCategoria().setIdCategoria(rs.getInt("id_categoria"));
                    articulo.setMarca(new Marca());
                    articulo.getMarca().setIdMarca(rs.getInt("id_marca"));
                    articulo.setImagen(rs.getString("imagen"));
                    articulo.setEstado(rs.getString("estado"));

                    return articulo;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }
    /**
     * Busca artículos en la base de datos por su descripción, utilizando coincidencia parcial.
     *
     * buscarArticuloPorDescripcion("descripcion")
     *  .thenAcceptAsync(articulos -> {
     *      if (articulos != null) {
     *          if (articulos.isEmpty()) {
     *              // Manejar el caso en que no se encontraron artículos
     *          } else {
     *              // Manejar la lista de artículos
     *          }
     *      } else {
     *          // Manejar el caso de error
     *      }
     *  });
     * 
     * @param descripcion Término de búsqueda para la descripción de los artículos.
     * @return CompletableFuture que contiene una lista de artículos que coinciden con la descripción.
     */
    public CompletableFuture<List<Articulo>> buscarArticuloPorDescripcion(String descripcion) {
        return CompletableFuture.supplyAsync(() -> {
            List<Articulo> articulos = new ArrayList<>();
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT * FROM Articulos WHERE descripcion LIKE ?")) {

                ps.setString(1, "%" + descripcion + "%");
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Articulo articulo = new Articulo();
                    articulo.setIdArticulo(rs.getInt("id_articulo"));
                    articulo.setDescripcion(rs.getString("descripcion"));
                    articulo.setComercio(new Comercio());
                    articulo.getComercio().setId(rs.getInt("id_comercio"));
                    articulo.setPrecio(rs.getDouble("precio"));
                    articulo.setCategoria(new Categoria());
                    articulo.getCategoria().setIdCategoria(rs.getInt("id_categoria"));
                    articulo.setMarca(new Marca());
                    articulo.getMarca().setIdMarca(rs.getInt("id_marca"));
                    articulo.setImagen(rs.getString("imagen"));
                    articulo.setEstado(rs.getString("estado"));

                    articulos.add(articulo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return articulos;
        });
    }
}
