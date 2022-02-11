package orm;

import annotations.primaryKey;
import utilities.ConnectionToDB;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ORM<E>  {

    private Class<E> entityClass;
    Connection connect = ConnectionToDB.getConnection();

    public ORM(Class<E> entityClass) throws SQLException, IOException {
        this.entityClass = entityClass;
    }

    /**
     * Retrieves the object data from the Database
     * @param obj Object model
     * @return Returns object data from DB as a new Instance of the object class
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    public E select(E obj) throws IllegalAccessException {
        StringBuilder query = new StringBuilder("SELECT * FROM ");
        Class<?> cl = obj.getClass();
        query.append(obj.getClass().getSimpleName().toLowerCase());
        query.append(" WHERE ");
        Field[] fields = cl.getDeclaredFields();
        for (Field f : fields){
            f.setAccessible(true);
            if (f.getType().getSimpleName().equals("String")){
                query.append(f.getName()).append("='").append(f.get(obj)).append("' and ");
            }
            else
            query.append(f.getName().toLowerCase()).append("=").append(f.get(obj)).append(" and ");
        }
        query.delete(query.length() - 4, query.length() - 1);
        query.append(";");
        String statement = query.toString();

        try {
            PreparedStatement ps         = connect.prepareStatement(statement);
            ResultSet res                = ps.executeQuery();
            ResultSetMetaData rsMetaData = res.getMetaData();
            int columns                  = rsMetaData.getColumnCount();

            E ob = (E) cl.newInstance();
            while(res.next()) ob = generateData(res, rsMetaData, columns, ob);
            return  ob;
        }
        catch (SQLException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Selects object data from DB by provided ID
     * @param obj Table to select from in DB
     * @param id  ID to get row data in DB
     * @return    Returns a new instance of object data class
     * @throws IllegalAccessException
     */
    public E selectByID(E obj, int id) throws IllegalAccessException {
        StringBuilder query = new StringBuilder("SELECT * FROM ");
        query.append(obj.getClass().getSimpleName().toLowerCase());
        query.append(" WHERE ");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f : fields){
            f.setAccessible(true);
            if (f.isAnnotationPresent(primaryKey.class)){
                query.append(f.getName()).append("=").append(id).append(";");
            }
        }
        String statement = query.toString();

        try {
            PreparedStatement ps         = connect.prepareStatement(statement);
            ResultSet res                = ps.executeQuery();
            ResultSetMetaData rsMetaData = res.getMetaData();
            int columns                  = rsMetaData.getColumnCount();

            E ob = (E) obj.getClass().newInstance();
            while(res.next()) ob = generateData(res, rsMetaData, columns, ob);
            return  ob;
        }
        catch (SQLException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Generates a List of all data in the database for the provided Table
     * @param obj Table reference to generate list from
     * @return The generated list of all data in a List<E> allData;
     */
    public List<E> selectAll(E obj){
        String query = "SELECT * FROM " + obj.getClass().getSimpleName() + ";";

        try {
            PreparedStatement ps      = connect.prepareStatement(query);
            ResultSet res             = ps.executeQuery();
            ResultSetMetaData resMeta = res.getMetaData();
            int count                 = resMeta.getColumnCount();
            List<E> allData =         new ArrayList<>();

            while (res.next()){
                E newObj                   = (E) obj.getClass().newInstance();
                newObj                     = generateData(res,resMeta,count,newObj);
                allData.add(newObj);
            }
            return allData;
        }
        catch (SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates/Inserts new object data into DB
     * @param obj The new object to be added/inserted to Database
     * @return True if successful, False if not
     * @throws IllegalAccessException
     */
    public E create(E obj) throws IllegalAccessException {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ");
        Class<?> cl = obj.getClass();
        query.append(cl.getSimpleName());
        query.append(" VALUES(default,");
        Field[] fields = cl.getDeclaredFields();
        for (Field fd : fields){
            fd.setAccessible(true);
            if (!fd.isAnnotationPresent(primaryKey.class)){
                if (fd.getType().getSimpleName().equals("String")){
                    query.append("'").append(fd.get(obj)).append("'").append(",");
                } else {
                    query.append(fd.get(obj)).append(",");
                }
            }
        }
        query.deleteCharAt(query.length() - 1);
        String statement = query + ") RETURNING *;";

        try {
            PreparedStatement ps = connect.prepareStatement(statement);

            ResultSet res = ps.executeQuery();
            ResultSetMetaData resMeta = res.getMetaData();
            int columns = resMeta.getColumnCount();
            E ob = (E) cl.newInstance();
            while (res.next()) ob = generateData(res,resMeta,columns, ob);
            return ob;
        }
        catch (SQLException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates the data of provided object in the DB.
     * @param obj Object to update in DataBase
     */
    public E update(E obj) throws IllegalAccessException {
        StringBuilder query = new StringBuilder();
        query.append("UPDATE ").append(obj.getClass().getSimpleName().toLowerCase()).append(" SET ");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field fd : fields){
            fd.setAccessible(true);
            if (!fd.isAnnotationPresent(primaryKey.class)){
                if (fd.getType().getSimpleName().equals("String")){
                    query.append(fd.getName()).append("='").append(fd.get(obj)).append("',");
                }
                else {
                    query.append(fd.getName()).append("=").append(fd.get(obj)).append(",");
                }
            }
        }
        query.deleteCharAt(query.length() - 1);
        for (Field f : fields){
            if (f.isAnnotationPresent(primaryKey.class)){
                query.append(" WHERE ").append(f.getName()).append("=").append(f.get(obj)).append(" RETURNING *;");
            }
        }

        String statement = query.toString();

        try {
            PreparedStatement ps      = connect.prepareStatement(statement);
            ResultSet res             = ps.executeQuery();
            ResultSetMetaData resMeta = res.getMetaData();
            int count                 = resMeta.getColumnCount();
            E newData = (E) obj.getClass().newInstance();
            while (res.next()) newData  = generateData(res, resMeta, count, newData);
            return newData;
        }
        catch (SQLException | InstantiationException e){
            e.printStackTrace();
        }
        return null;
        }


    /**
     * Deletes data if all conditions are met in database
     * @param obj The object(data) to be deleted
     * @return The deleted data in an instance of the Class
     * @throws IllegalAccessException
     */
    public E delete(E obj) throws IllegalAccessException {
        StringBuilder query = new StringBuilder("DELETE FROM ");
        Class<?> cl = obj.getClass();
        query.append(obj.getClass().getSimpleName().toLowerCase());
        query.append(" WHERE ");
        Field[] fields = cl.getDeclaredFields();
        for (Field f : fields){
            f.setAccessible(true);
            if (f.getType().getSimpleName().equals("String")){
                query.append(f.getName()).append("='").append(f.get(obj)).append("' and ");
            }
            else
                query.append(f.getName().toLowerCase()).append("=").append(f.get(obj)).append(" and ");
        }
        query.delete(query.length() - 4, query.length() - 1);
        query.append("RETURNING *;");
        String statement = query.toString();

        try {
            PreparedStatement ps         = connect.prepareStatement(statement);
            ResultSet res                = ps.executeQuery();
            ResultSetMetaData rsMetaData = res.getMetaData();
            int columns                  = rsMetaData.getColumnCount();

            E ob = (E) cl.newInstance();
            while(res.next()) ob = generateData(res, rsMetaData, columns, ob);
            return  (E) ob;
        }
        catch (SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Also deletes data/object from database
     * @param entityClass Class(Table in DB) of the entity to be deleted from database
     * @param id Deletes data with this provided ID
     * @return Deleted data in a newInstance of the Class
     * @throws IllegalAccessException
     */
    public E deleteByID(E entityClass, int id) throws IllegalAccessException {

            StringBuilder query = new StringBuilder("DELETE FROM ");
            query.append(entityClass.getClass().getSimpleName().toLowerCase());
            query.append(" WHERE ");
            Field[] fields = entityClass.getClass().getDeclaredFields();
            for (Field f : fields){
                f.setAccessible(true);
                if (f.isAnnotationPresent(primaryKey.class))
                query.append(f.getName().toLowerCase()).append("=").append(id);
            }
            query.append(" RETURNING *;");
            String statement = query.toString();

            try {
                PreparedStatement ps         = connect.prepareStatement(statement);
                ResultSet res                = ps.executeQuery();
                ResultSetMetaData rsMetaData = res.getMetaData();
                int columns                  = rsMetaData.getColumnCount();

                E ob = (E) entityClass.getClass().newInstance();
                while(res.next()) ob = generateData(res, rsMetaData, columns, ob);
                return ob;
            }
            catch (SQLException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }


    /**
     * Helps generate data from DataBase
     * @param res       ->  The ResultSet to retrieve data
     * @param rsMetaData -> A ResultSetMetaData to get the number of columns in the ResultSet
     * @param columns    -> The number of columns to get columnNames
     * @param ob         -> Object/entity to place generated data in
     * @return           -> Returns the generated data in an entity provided above
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private E generateData(ResultSet res, ResultSetMetaData rsMetaData, int columns, E ob) throws SQLException, IllegalAccessException, InstantiationException {
       E newInstance = (E) ob.getClass().newInstance();
        Field[] fds = ob.getClass().getDeclaredFields();
        for (Field f : fds){
            f.setAccessible(true);
            for (int i = 1; i <= columns; i++){
                if (f.getName().toLowerCase().equals(rsMetaData.getColumnName(i))){
                    switch (f.getType().getSimpleName()) {
                        case "String":
                                f.set(ob, res.getString(i));
                            break;

                        case "int":
                                f.setInt(ob, res.getInt(i));
                            break;

                        case "boolean":{
                            f.setBoolean(ob, res.getBoolean(i));
                        }
                        break;

                        case "double":
                                f.setDouble(ob, res.getDouble(i));
                            break;

                        case "long":
                                long dt = res.getLong(i);
                                f.setLong(ob, dt);
                            break;

                        default:
                            f.set(ob, null);
                    }
                }
            }
        }
        newInstance = ob;
        return newInstance;
    }



}
