package JSON;


import org.json.JSONArray;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class JSONUtilities {

    public static void GrabarJSON(JSONArray jsonArray, String nombreArchivo){

        try(FileWriter fileWriter = new FileWriter(nombreArchivo)){
            fileWriter.write(jsonArray.toString(4));
            fileWriter.flush();

        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public static JSONTokener leerArchivo(String nombreArchivo){
        JSONTokener jsonTokener = null;
        try{
            jsonTokener = new JSONTokener(new FileReader(nombreArchivo));
        }catch (IOException e){
            e.printStackTrace();
        }

        return jsonTokener;
    }

}
