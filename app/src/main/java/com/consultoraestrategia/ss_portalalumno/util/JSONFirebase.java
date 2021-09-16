package com.consultoraestrategia.ss_portalalumno.util;

import com.consultoraestrategia.ss_portalalumno.entities.firebase.FBSesionAprendizaje;
import com.google.firebase.database.DataSnapshot;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JSONFirebase  {

    private final JsonElement jsonElement;

    private JSONFirebase(JsonElement  jsonElement) {
        this.jsonElement = jsonElement;
    }

    public static JSONFirebase d(JsonElement jsonElement){
        return new JSONFirebase(jsonElement);
    }

    public Map<String,JSONFirebase> getChildren2() {
        Map<String, JSONFirebase> attributes = new HashMap<>();
        if(jsonElement == null){

        }else if(jsonElement.isJsonObject()){
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            for(Map.Entry<String,JsonElement> entry : jsonObject.entrySet()){
                attributes.put(entry.getKey(), d(jsonObject.get(entry.getKey()).getAsJsonObject()));
            }
        }else if(jsonElement.isJsonArray()){
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (JsonElement jsonElement: jsonArray) {
                attributes.put("0",d(jsonElement));
            }
        }

        return attributes;
    }

    public List<JSONFirebase> getChildren() {
        List<JSONFirebase> attributes = new ArrayList<>();
        if(jsonElement == null){

        }else if(jsonElement.isJsonObject()){
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            for(Map.Entry<String,JsonElement> entry : jsonObject.entrySet()){
                attributes.add(d(jsonObject.get(entry.getKey()).getAsJsonObject()));
            }
        }else if(jsonElement.isJsonArray()){
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (JsonElement jsonElement: jsonArray) {
                attributes.add(d(jsonElement));
            }
        }
        return attributes;
    }

    public JsonElement getJsonElement() {
        return jsonElement;
    }

    public JSONFirebase child(String params) {
        if(jsonElement == null){
            return d(null);
        }else if(jsonElement.isJsonObject()){
            //JsonElement jsonElement = jsonObject.get(params);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            if(jsonObject.has(params)){
                return d(jsonObject.get(params));
            }else {
                return d(null);
            }
        }else {
            return d(null);
        }

    }

    public <T> T getValue(Class<T> aClass) {
        return new Gson().fromJson(jsonElement, aClass);
    }
    public Object getValue() {
        return jsonElement;
    }


    public boolean exists() {
        return jsonElement!=null;
    }


}
